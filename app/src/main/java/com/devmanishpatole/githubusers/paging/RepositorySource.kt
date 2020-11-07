package com.devmanishpatole.githubusers.paging

import android.content.Context
import com.devmanishpatole.githubusers.base.BaseDataSource
import com.devmanishpatole.githubusers.exception.NetworkException
import com.devmanishpatole.githubusers.model.RepositoryItem
import com.devmanishpatole.githubusers.service.GitHubService

class RepositorySource(
    private val service: GitHubService,
    private val name: String,
    context: Context,
) : BaseDataSource<RepositoryItem>(context) {

    override suspend fun loadFromLocalStorage(): List<RepositoryItem>? {
        // In case local cache implemented
        return null
    }

    override suspend fun loadFromNetwork(): List<RepositoryItem>? {
        val results: List<RepositoryItem>?

        if (connectivityLiveData.value != null && connectivityLiveData.value == false) {
            throw NetworkException()
        }

        val response = service.repos(name, page = position)

        if (response.isSuccessful) {
            results = response.body()
        } else {
            throw Exception()
        }

        return results
    }

    override fun getPreviousKey() =
        if (position == STARTING_PAGE_INDEX) null else position - 1

    override fun getNextKey(results: List<RepositoryItem>) =
        if (results.isEmpty() || results.size < THRESHOLD) null else position + 1
}