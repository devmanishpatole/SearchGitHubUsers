package com.devmanishpatole.githubusers.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.adapter.UserListAdapter
import com.devmanishpatole.githubusers.base.PagingFragment
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.viewholder.UserViewHolder
import com.devmanishpatole.githubusers.viewmodel.FollowersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list.*

@AndroidEntryPoint
class FollowersFragment : PagingFragment<FollowersViewModel, GitHubUser, UserViewHolder>() {

    override val viewModel: FollowersViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_followers

    companion object {
        private const val USER_NAME = "USER_NAME"
        fun newInstance(name: String) = FollowersFragment().apply {
            arguments = bundleOf(USER_NAME to name)
        }
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        super.setupView(view, savedInstanceState)
        arguments?.apply {
            getString(USER_NAME)?.let { viewModel.searchFollowers(it) }
        }
    }

    override fun getAdapter() = UserListAdapter(viewLifecycleOwner.lifecycle)

    override fun observeData() {
        viewModel.followers.observe(viewLifecycleOwner) {
            listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun getErrorMessage() = R.string.no_followers

    override fun getList(): RecyclerView = list

    override fun notLoading() {
        // No Implementation
    }
}