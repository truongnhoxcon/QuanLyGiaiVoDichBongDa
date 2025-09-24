package com.example.quanlygiaivodichbongda.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingImpl(
    private val context: Context
) : AppSetting {

    override val data: Flow<AppSettingData> =
        context.appSettingsDataStore.data.map { pref ->
            AppSettingData(
                isNotificationEnabled = pref[AppSettingDataStoreKeys.NOTIFICATION_ENABLED] ?: true
            )
        }

    override suspend fun setNotificationEnabled(enabled: Boolean) {
        context.appSettingsDataStore.edit { pref ->
            pref[AppSettingDataStoreKeys.NOTIFICATION_ENABLED] = enabled
        }
    }
}