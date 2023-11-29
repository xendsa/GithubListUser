package com.example.submissionfundamental1.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamental1.data.model.UserResponse
import com.example.submissionfundamental1.databinding.FragmentFollowBinding
import com.example.submissionfundamental1.ui.main.Result
import com.example.submissionfundamental1.ui.main.UserAdapter

class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val adapter = UserAdapter {
    }

    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }

        followData()
    }

    private fun followData() {
        val observeLiveData = when (type) {
            FOLLOWERS -> viewModel.resultFollowers
            FOLLOWING -> viewModel.resultFollowing
            else -> throw IllegalArgumentException("Invalid type: $type")
        }

        observeLiveData.observe(viewLifecycleOwner, this::manageResultFollows)
    }

    private fun manageResultFollows(state: Result) {
        when (state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<UserResponse.Item>)
            }

            is Result.Error -> {
                Toast.makeText(
                    requireActivity(),
                    state.exception.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is Result.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWERS = 101
        const val FOLLOWING = 100

        fun newInstance(type: Int) = FollowFragment()
            .apply {
                this.type = type
            }
    }
}