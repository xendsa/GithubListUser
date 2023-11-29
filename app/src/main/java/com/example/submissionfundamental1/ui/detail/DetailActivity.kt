package com.example.submissionfundamental1.ui.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submissionfundamental1.R
import com.example.submissionfundamental1.data.local.DbModule
import com.example.submissionfundamental1.data.model.UserResponse
import com.example.submissionfundamental1.data.model.UserResponseDetail
import com.example.submissionfundamental1.databinding.ActivityDetailBinding
import com.example.submissionfundamental1.ui.main.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DbModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<UserResponse.Item>("item")
        val username = item?.login ?: ""

        viewModel.getDetailUser(username)
        viewModel.getFollowers(username)
        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as UserResponseDetail
                    binding.ivProfile.load(user.avatarUrl) {
                        transformations(CircleCropTransformation())
                    }

                    binding.tvName.text = user.name
                    binding.tvUsername.text = user.login
                    binding.tvTotalFollowers.text =
                        getString(R.string.followers_template, user.followers.toString())
                    binding.tvTotalFollowing.text =
                        getString(R.string.following_template, user.following.toString())
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }
        getDetailUser(username)
        setFavChange(item?.id ?: 0)
        setFavButton(item)
    }

    private fun getDetailUser(username: String) {
        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )
        val titleFragment = mutableListOf(
            getString(R.string.followers_data),
            getString(R.string.following_data)
        )
        val adapter = SectionsPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setFavChange(userId: Int) {
        viewModel.resultUserList.observe(this) { userOnList ->
            val setColor = if (userOnList) R.color.red
            else R.color.white
            binding.btnFavorite.changeIconColor(setColor)
        }

        viewModel.resultFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.resultDeleteFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        viewModel.findFavorite(userId) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.checkUser(userId)
    }

    private fun setFavButton(itemUser: UserResponse.Item?){
        binding.btnFavorite.setOnClickListener{
            viewModel.setFavorite(itemUser)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}
