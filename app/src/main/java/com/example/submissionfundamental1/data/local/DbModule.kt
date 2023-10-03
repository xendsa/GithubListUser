package com.example.submissionfundamental1.data.local

import android.content.Context
import androidx.room.Room

class DbModule(private val context: Context) {
    private val  db = Room.databaseBuilder(context, DbApp::class.java, "submissiongit.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}