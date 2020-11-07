package com.devmanishpatole.githubusers.paging

import android.content.Context
import com.devmanishpatole.githubusers.base.BaseDataSource
import com.devmanishpatole.githubusers.exception.NetworkException
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.service.GitHubService

class FollowerSource(
    private val service: GitHubService,
    private val name: String,
    context: Context,
) : BaseDataSource<GitHubUser>(context) {

    override suspend fun loadFromLocalStorage(): List<GitHubUser>? {
        // In case local cache implemented
        return null
    }

    override suspend fun loadFromNetwork(): List<GitHubUser>? {
        val results: List<GitHubUser>?

        if (connectivityLiveData.value != null && connectivityLiveData.value == false) {
            throw NetworkException()
        }

        val response = service.followers(name, page = position)

        if (response.isSuccessful) {
            results = response.body()
        } else {
            throw Exception()
        }

        return results
    }

    override fun getPreviousKey() =
        if (position == STARTING_PAGE_INDEX) null else position - 1

    override fun getNextKey(results: List<GitHubUser>) =
        if (results.isEmpty() || results.size < THRESHOLD) null else position + 1
}