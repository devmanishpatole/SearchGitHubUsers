package com.devmanishpatole.githubusers.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import com.devmanishpatole.githubusers.base.BasePagingAdapter
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.viewholder.UserViewHolder

/**
 * Adapter for GitHub users list.
 */
class UserListAdapter(parentLifecycle: Lifecycle) :
    BasePagingAdapter<GitHubUser, UserViewHolder>(parentLifecycle, USER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(parent, ::onClick)

    lateinit var onItemClick: (GitHubUser) -> Unit

    /**
     * Callback implementation to send back the selected GitHub user.
     */
    private fun onClick(position: Int) {
        if (::onItemClick.isInitialized) {
            val user = getItem(position)
            user?.let { onItemClick(it) }
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<GitHubUser>() {
            override fun areItemsTheSame(
                oldItem: GitHubUser,
                newItem: GitHubUser
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GitHubUser,
                newItem: GitHubUser
            ) = oldItem == newItem
        }
    }
}