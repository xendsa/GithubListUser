package com.example.submissionfundamental1.ui.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreference constructor(val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun setThemeMode(): Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeMode(isDark: Boolean){
        dataStore.edit { preference ->
            preference[THEME_KEY] = isDark
        }
    }
}