package com.example.homeworks.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.homeworks.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.pb",
    serializer = UserPreferencesSerializer
)

class DataStoreManager(private val context: Context) {

    private val dataStore: DataStore<UserPreferences> = context.userPreferencesDataStore

    suspend fun saveUser(firstName: String, lastName: String, email: String) {
        try {
            dataStore.updateData { preferences ->
                preferences.toBuilder()
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .build()
            }
        } catch (_: Exception) {
        }
    }

    val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences
        }
}
