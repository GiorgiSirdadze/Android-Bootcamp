package com.example.homeworks.domain.preference_key

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
     val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
     val EMAIL = stringPreferencesKey("email")
}