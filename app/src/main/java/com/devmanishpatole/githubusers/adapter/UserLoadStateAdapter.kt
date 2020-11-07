package com.devmanishpatole.githubusers.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.devmanishpatole.githubusers.viewholder.UserLoadStateViewHolder

/**
 * Adapter to handle lazy loading buffering and error.
 */
class UserLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UserLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = UserLoadStateViewHolder.create(parent, retry)
}