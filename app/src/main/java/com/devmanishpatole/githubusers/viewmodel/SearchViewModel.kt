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

/**
 * Class helps searching GitHub users.
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: GitHubPagingRepository,
    @Assisted state: SavedStateHandle
) : BaseViewModel() {

    private val currentUserName: MutableLiveData<String> = state.getLiveData(RESTORED_USER_NAME)

    val users = currentUserName.switchMap { name ->
        repository.getUsers(name).cachedIn(viewModelScope)
    }

    /**
     * Starts searching users
     *
     * @param name to search
     */
    fun searchUsers(name: String) {
        currentUserName.value = name
    }

    companion object {
        private const val RESTORED_USER_NAME = "RESTORED_USER_NAME"
    }
}