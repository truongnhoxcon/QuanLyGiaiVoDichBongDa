package com.example.quanlygiaivodichbongda.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

object AppSettingDataStoreKeys {
    val NOTIFICATION_ENABLED: Preferences.Key<Boolean> =
        booleanPreferencesKey("notification_enabled")
}