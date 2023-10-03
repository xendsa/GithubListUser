package com.example.submissionfundamental1.ui.user

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(
    fa: FragmentActivity,
    private val fragment: MutableList<Fragment>
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }

}