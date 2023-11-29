package com.example.submissionfundamental1.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fa: FragmentActivity, private val fragment: MutableList<Fragment>) :
    FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }

    override fun getItemCount(): Int {
        return fragment.size
    }
}