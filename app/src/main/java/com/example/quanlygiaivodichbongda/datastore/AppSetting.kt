package com.example.quanlygiaivodichbongda.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

interface AppSetting {
    val data: Flow<AppSettingData>
    suspend fun setNotificationEnabled(enabled: Boolean)
}

private const val DATA_STORE_NAME = "app_settings"

val Context.appSettingsDataStore by preferencesDataStore(name = DATA_STORE_NAME)