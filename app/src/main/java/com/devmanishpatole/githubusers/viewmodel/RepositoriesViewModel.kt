package com.devmanishpatole.githubusers.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devmanishpatole.githubusers.base.BaseViewModel
import com.devmanishpatole.githubusers.repository.GitHubPagingRepository

class RepositoriesViewModel @ViewModelInject constructor(
    private val repository: GitHubPagingRepository,
    @Assisted state: SavedStateHandle
) : BaseViewModel(){

    private val currentUserName: MutableLiveData<String> = state.getLiveData(RESTORED_USER_NAME)

    val followers = currentUserName.switchMap { name ->
        repository.getRepositories(name).cachedIn(viewModelScope)
    }

    /**
     * Starts searching repositories
     *
     * @param name to search
     */
    fun searchRepositories(name: String) {
        currentUserName.value = name
    }

    companion object {
        private const val RESTORED_USER_NAME = "RESTORED_USER_NAME"
    }

}