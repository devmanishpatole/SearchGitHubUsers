package com.devmanishpatole.githubusers.viewholder

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.base.BaseItemViewHolder
import com.devmanishpatole.githubusers.model.RepositoryItem
import com.devmanishpatole.githubusers.viewmodel.RepositoryItemViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.internal.managers.ViewComponentManager
import kotlinx.android.synthetic.main.row_repository.view.*

class RepositoryViewHolder(parent: ViewGroup) :
    BaseItemViewHolder<RepositoryItem, RepositoryItemViewModel>(R.layout.row_repository, parent) {

    override lateinit var lifecycleRegistry: LifecycleRegistry

    override lateinit var viewModel: RepositoryItemViewModel

    override fun setupView(view: View) {
        // No Implementation
    }

    override fun bind(data: RepositoryItem) {
        super.bind(data)
        with(itemView) {
            repoName.text = data.name
            repoDesc.text = data.description
            language.text = data.language
        }
    }

    @InstallIn(FragmentComponent::class)
    @EntryPoint
    interface ProviderRepositoryItemViewModel {
        fun repositoryItemViewModel(): RepositoryItemViewModel
    }

    private fun getUserItemViewModel(fragment: Fragment): RepositoryItemViewModel {
        val hiltEntryPoint = EntryPointAccessors.fromFragment(
            fragment, ProviderRepositoryItemViewModel::class.java
        )
        return hiltEntryPoint.repositoryItemViewModel()
    }

    override fun injectDependency() {
        lifecycleRegistry = LifecycleRegistry(this)
        viewModel =
            getUserItemViewModel((itemView.context as ViewComponentManager.FragmentContextWrapper).fragment)
    }

}