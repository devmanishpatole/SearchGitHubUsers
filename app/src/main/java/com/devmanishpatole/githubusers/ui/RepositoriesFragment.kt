package com.devmanishpatole.githubusers.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.adapter.RepositoryAdapter
import com.devmanishpatole.githubusers.base.PagingFragment
import com.devmanishpatole.githubusers.model.RepositoryItem
import com.devmanishpatole.githubusers.viewholder.RepositoryViewHolder
import com.devmanishpatole.githubusers.viewmodel.RepositoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list.*

@AndroidEntryPoint
class RepositoriesFragment :
    PagingFragment<RepositoriesViewModel, RepositoryItem, RepositoryViewHolder>() {

    override val viewModel: RepositoriesViewModel by viewModels()

    override fun getLayoutId() = R.layout.fragment_repositories

    companion object {
        private const val USER_NAME = "USER_NAME"
        fun newInstance(name: String) = RepositoriesFragment().apply {
            arguments = bundleOf(USER_NAME to name)
        }
    }

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        super.setupView(view, savedInstanceState)
        arguments?.apply {
            getString(USER_NAME)?.let { viewModel.searchRepositories(it) }
        }
    }

    override fun getAdapter() = RepositoryAdapter(viewLifecycleOwner.lifecycle)

    override fun observeData() {
        viewModel.followers.observe(viewLifecycleOwner) {
            listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun getErrorMessage() = R.string.no_user_found

    override fun getList(): RecyclerView = list

    override fun notLoading() {
        // No Implementation
    }
}