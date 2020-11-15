package com.devmanishpatole.githubusers.di

import android.content.Context
import com.devmanishpatole.githubusers.BuildConfig
import com.devmanishpatole.githubusers.network.Networking
import com.devmanishpatole.githubusers.service.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val ACCEPT = "Accept"
    private const val ACCEPT_VALUE = "application/vnd.github.v3+json"

    private fun getInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder()
                .header(
                    ACCEPT,
                    ACCEPT_VALUE
                )
                .build()
            return chain.proceed(request)
        }
    }

    private fun getLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideNetwork(
        @ApplicationContext appContext: Context
    ): Retrofit =
        Networking.create(
            BuildConfig.BASE_URL,
            appContext.cacheDir,
            10 * 1024 * 1024, // 10MB
            getLoggingInterceptor(),
            getInterceptor()
        )

    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): GitHubService =
        retrofit.create(GitHubService::class.java)

}