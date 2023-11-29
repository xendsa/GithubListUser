package com.example.submissionfundamental1.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreference) : ViewModel() {

    fun setTheme(): LiveData<Boolean>{
        return pref.setThemeMode().asLiveData()
    }

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            pref.saveThemeMode(isDark)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val pref: SettingPreference) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
                return SettingViewModel(pref) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
