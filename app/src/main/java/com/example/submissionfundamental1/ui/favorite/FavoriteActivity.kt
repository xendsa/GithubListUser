package com.example.submissionfundamental1.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamental1.data.local.DbModule
import com.example.submissionfundamental1.databinding.ActivityFavoriteBinding
import com.example.submissionfundamental1.ui.detail.DetailActivity
import com.example.submissionfundamental1.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>{
        FavoriteViewModel.Factory(DbModule(this))
    }
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java)
                .apply {
                    putExtra("item", user)
                    startActivity(this)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        viewModel.getFavorite().observe(this){
            adapter.setData(it)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavorite().observe(this){favList ->
            adapter.setData(favList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}