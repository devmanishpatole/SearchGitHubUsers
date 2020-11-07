package com.devmanishpatole.githubusers.service

import com.devmanishpatole.githubusers.model.FollowersWrapper
import com.devmanishpatole.githubusers.model.GitHubUserWrapper
import com.devmanishpatole.githubusers.model.RepositoryWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") search: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): Response<GitHubUserWrapper>

    @GET("users/{name}/repos")
    suspend fun repos(
        @Path("name") name: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): Response<RepositoryWrapper>

    @GET("users/{name}/followers")
    suspend fun followers(
        @Path("name") name: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): Response<FollowersWrapper>
}