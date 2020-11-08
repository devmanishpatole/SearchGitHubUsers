package com.devmanishpatole.githubusers.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.devmanishpatole.githubusers.paging.FollowerSource
import com.devmanishpatole.githubusers.paging.RepositorySource
import com.devmanishpatole.githubusers.paging.UserSource
import com.devmanishpatole.githubusers.service.GitHubService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Git repository implementation with Paging3.
 */
class GitHubPagingRepositoryImpl @Inject constructor(
    private val service: GitHubService,
    @ApplicationContext private val context: Context
) : GitHubPagingRepository {

    /**
     * Fetches users.
     * @param name to search using UserSource
     */
    override fun getUsers(name: String) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            UserSource(service, name, context)
        }
    ).liveData

    /**
     * Fetches followers.
     *  @param name to search using FollowersSource
     */
    override fun getFollowers(name: String) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            FollowerSource(service, name, context)
        }
    ).liveData

    /**
     * Fetches public repositories.
     *  @param name to search using RepositorySource
     */
    override fun getRepositories(name: String) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            RepositorySource(service, name, context)
        }
    ).liveData

    companion object {
        private const val PAGE_SIZE = 100
    }
}