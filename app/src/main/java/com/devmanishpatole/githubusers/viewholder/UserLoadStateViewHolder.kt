package com.devmanishpatole.githubusers.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.devmanishpatole.githubusers.R

class UserLoadStateViewHolder(
    itemView: View, retry: () -> Unit,
) : RecyclerView.ViewHolder(itemView) {

    private val retryButton: ImageView = itemView.findViewById(R.id.retry_button)
    private val errorText: TextView = itemView.findViewById(R.id.error_msg)
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

    init {
        retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorText.text = loadState.error.localizedMessage
        }
        progressBar.isVisible = loadState is LoadState.Loading
        retryButton.isVisible = loadState !is LoadState.Loading
        errorText.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): UserLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_view_item, parent, false)
            return UserLoadStateViewHolder(view, retry)
        }
    }
}