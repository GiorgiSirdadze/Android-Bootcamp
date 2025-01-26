package com.example.homeworks

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveLoginState(isLoggedIn: Boolean, email: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
            preferences[EMAIL] = email
        }
    }

    suspend fun clearLoginState() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val email: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[EMAIL] ?: ""
    }
}
