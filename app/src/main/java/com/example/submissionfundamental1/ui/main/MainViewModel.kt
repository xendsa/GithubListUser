package com.example.submissionfundamental1.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamental1.data.api.ApiConfig
import com.example.submissionfundamental1.ui.setting.SettingPreference
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: SettingPreference) : ViewModel() {

    val listDataUser = MutableLiveData<Result>()

    fun setTheme() = preferences.setThemeMode().asLiveData()

    fun getUser(username: String) {
        viewModelScope.launch {
            try {
                listDataUser.value = Result.Loading(true)

                val response = ApiConfig.gitHubService.getSearchUser(
                    mapOf(
                        "q" to username,
                        "per_page" to 15
                    )
                )

                listDataUser.value = Result.Loading(false)
                listDataUser.value = Result.Success(response.items)
            } catch (e: Throwable) {
                Log.e("Error", e.message.toString())
                e.printStackTrace()
                listDataUser.value = Result.Error(e)
            }
        }
    }

    class Factory(private val preferences: SettingPreference) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}