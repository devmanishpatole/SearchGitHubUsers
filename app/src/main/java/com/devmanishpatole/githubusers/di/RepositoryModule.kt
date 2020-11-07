package com.devmanishpatole.githubusers.di

import com.devmanishpatole.githubusers.repository.GitHubPagingRepository
import com.devmanishpatole.githubusers.repository.GitHubPagingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideGitHubPagingRepository(gitHubPagingRepository: GitHubPagingRepositoryImpl): GitHubPagingRepository
}