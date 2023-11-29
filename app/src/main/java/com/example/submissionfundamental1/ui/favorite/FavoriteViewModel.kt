package com.example.submissionfundamental1.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionfundamental1.data.local.DbModule

class FavoriteViewModel(private val dbModule: DbModule) : ViewModel() {

    fun getFavorite() = dbModule.userDao.loadAll()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}