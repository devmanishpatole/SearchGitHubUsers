package com.devmanishpatole.githubusers.viewholder

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.base.BaseItemViewHolder
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.util.load
import com.devmanishpatole.githubusers.viewmodel.GitHubUserItemViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.internal.managers.ViewComponentManager
import kotlinx.android.synthetic.main.row_user.view.*

class UserViewHolder(parent: ViewGroup, private val onItemClick: (Int) -> Unit) :
    BaseItemViewHolder<GitHubUser, GitHubUserItemViewModel>(R.layout.row_user, parent) {

    override lateinit var lifecycleRegistry: LifecycleRegistry

    override lateinit var viewModel: GitHubUserItemViewModel

    override fun setupView(view: View) {
        // No Implementation
    }

    override fun bind(data: GitHubUser) {
        super.bind(data)
        with(itemView) {
            userImage.load(data.imageUrl)
            userName.text = data.name

            setOnClickListener {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    onItemClick(layoutPosition)
                }
            }
        }
    }

    @InstallIn(FragmentComponent::class)
    @EntryPoint
    interface ProviderUserItemViewModel {
        fun userItemViewModel(): GitHubUserItemViewModel
    }

    private fun getUserItemViewModel(fragment: Fragment): GitHubUserItemViewModel {
        val hiltEntryPoint = EntryPointAccessors.fromFragment(
            fragment, ProviderUserItemViewModel::class.java
        )
        return hiltEntryPoint.userItemViewModel()
    }

    override fun injectDependency() {
        lifecycleRegistry = LifecycleRegistry(this)
        viewModel =
            getUserItemViewModel((itemView.context as ViewComponentManager.FragmentContextWrapper).fragment)
    }

}