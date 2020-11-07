package com.devmanishpatole.githubusers.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import com.devmanishpatole.githubusers.base.BasePagingAdapter
import com.devmanishpatole.githubusers.model.RepositoryItem
import com.devmanishpatole.githubusers.viewholder.RepositoryViewHolder

/**
 * Adapter for repository list.
 */
class RepositoryAdapter(parentLifecycle: Lifecycle) :
    BasePagingAdapter<RepositoryItem, RepositoryViewHolder>(
        parentLifecycle,
        REPOSITORY_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepositoryViewHolder(parent)

    companion object {
        private val REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<RepositoryItem>() {
            override fun areItemsTheSame(
                oldItem: RepositoryItem,
                newItem: RepositoryItem
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: RepositoryItem,
                newItem: RepositoryItem
            ) = oldItem == newItem
        }
    }
}