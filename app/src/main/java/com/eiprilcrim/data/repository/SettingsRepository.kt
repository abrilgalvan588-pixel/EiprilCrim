package com.eiprilcrim.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

class SettingsRepository(private val context: Context) {

    private val lightModeKey = booleanPreferencesKey("is_light_mode_enabled")

    val isLightModeEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[lightModeKey] ?: false
        }

    suspend fun setLightModeEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[lightModeKey] = enabled
        }
    }
}
