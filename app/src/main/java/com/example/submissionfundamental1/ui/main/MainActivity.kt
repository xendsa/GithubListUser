package com.example.submissionfundamental1.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamental1.R
import com.example.submissionfundamental1.data.model.UserResponse
import com.example.submissionfundamental1.databinding.ActivityMainBinding
import com.example.submissionfundamental1.ui.detail.DetailActivity
import com.example.submissionfundamental1.ui.favorite.FavoriteActivity
import com.example.submissionfundamental1.ui.setting.SettingActivity
import com.example.submissionfundamental1.ui.setting.SettingPreference
import com.example.submissionfundamental1.ui.setting.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter{ user ->
            Intent(this, DetailActivity::class.java)
                .apply {
                    putExtra("item", user)
                    startActivity(this)
                }
        }
    }

    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(SettingPreference(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        setTheme()
        getListData()
        getSearchData()

        viewModel.getUser("kai")
    }

    private fun getSearchData() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               query?.let {
                   viewModel.getUser(it)
               }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setTheme() {
        viewModel.setTheme().observe(this) {
            val nightMode = if (it) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    private fun getListData() {
        viewModel.listDataUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val data = it.data as MutableList<UserResponse.Item>
                    adapter.setData(data)
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intentToFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentToFavorite)
            }

            R.id.setTheme -> {
                val intentToSetting = Intent(this, SettingActivity::class.java)
                startActivity(intentToSetting)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}