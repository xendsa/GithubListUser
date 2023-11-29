package com.example.submissionfundamental1.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamental1.data.api.ApiConfig
import com.example.submissionfundamental1.data.local.DbModule
import com.example.submissionfundamental1.data.model.UserResponse
import com.example.submissionfundamental1.ui.main.Result
import kotlinx.coroutines.launch

class DetailViewModel(private val db: DbModule) : ViewModel() {

    val resultUserList: LiveData<Boolean> = MutableLiveData()
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowers = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()
    val resultFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            try {
                val response = ApiConfig
                    .gitHubService
                    .getDetailUser(username)

                resultDetailUser.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                e.printStackTrace()
                resultDetailUser.value = Result.Error(e)
            } finally {
                resultDetailUser.value = Result.Loading(false)
            }
        }
    }


    fun getFollowers(username: String) {
        viewModelScope.launch {
            try {
                resultFollowers.value = Result.Loading(true)

                val response = ApiConfig
                    .gitHubService
                    .getFollowers(username)

                resultFollowers.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                e.printStackTrace()
                resultFollowers.value = Result.Error(e)
            } finally {
                resultFollowers.value = Result.Loading(false)
            }
        }
    }


    fun getFollowing(username: String) {
        viewModelScope.launch {
            try {
                resultFollowing.value = Result.Loading(true)

                val response = ApiConfig
                    .gitHubService
                    .getFollowing(username)

                resultFollowing.value = Result.Success(response)
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                e.printStackTrace()
                resultFollowing.value = Result.Error(e)
            } finally {
                resultFollowing.value = Result.Loading(false)
            }
        }
    }

    fun checkUser(userId: Int) {
        viewModelScope.launch {
            val count = db.userDao.userOnList(userId)
            (resultUserList as MutableLiveData).postValue(count > 0)
        }
    }


    fun setFavorite(item: UserResponse.Item?) {
        viewModelScope.launch {
            item?.let {
                if (isFavorite) {
                    db.userDao.delete(item)
                    resultDeleteFavorite.value = false
                } else {
                    db.userDao.insert(item)
                    resultFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null) {
                listenFavorite()
                isFavorite = true
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}
