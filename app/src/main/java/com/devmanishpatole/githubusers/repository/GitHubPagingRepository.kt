package com.devmanishpatole.githubusers.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.model.RepositoryItem
import javax.inject.Singleton

@Singleton
interface GitHubPagingRepository {
    /**
     * Return GitHub users
     *
     * @param name to search
     */
    fun getUsers(name: String): LiveData<PagingData<GitHubUser>>

    /**
     * Return GitHub users followers
     *
     * @param name to search
     */
    fun getFollowers(name: String): LiveData<PagingData<GitHubUser>>

    /**
     * Return GitHub users followers
     *
     * @param name to search
     */
    fun getRepositories(name: String): LiveData<PagingData<RepositoryItem>>
}