package com.devmanishpatole.githubusers.base

import android.content.Context
import androidx.paging.PagingSource
import com.devmanishpatole.githubusers.exception.NetworkException
import com.devmanishpatole.githubusers.util.NetworkUtils

abstract class BaseDataSource<T : Any>(context: Context) : PagingSource<Int, T>() {

    var results: List<T>? = null
    var position = STARTING_PAGE_INDEX

    protected val connectivityLiveData by lazy {
        NetworkUtils.observeConnectivity(context)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        position = params.key ?: STARTING_PAGE_INDEX

        return try {
            results = loadFromLocalStorage()

            if (results == null || results?.isEmpty() == true) {
                results = loadFromNetwork() ?: emptyList()
            }

            LoadResult.Page(
                data = results!!,
                prevKey = getPreviousKey(),
                nextKey = getNextKey(results!!)
            )
        } catch (exception: NetworkException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    abstract suspend fun loadFromLocalStorage(): List<T>?

    abstract suspend fun loadFromNetwork(): List<T>?

    abstract fun getPreviousKey(): Int?

    abstract fun getNextKey(results: List<T>): Int?

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val THRESHOLD = 100
    }

}