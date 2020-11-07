package com.devmanishpatole.githubusers.base

import android.os.Bundle
import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.adapter.UserLoadStateAdapter
import com.devmanishpatole.githubusers.exception.NetworkException


abstract class PagingFragment<VM : BaseViewModel, T : Any, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>> :
    BaseFragment<VM>() {

    lateinit var listAdapter: BasePagingAdapter<T, VH>

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        initList()
        addLoadStateChangeListener()
        observeData()
    }

    protected open fun initList() {
        listAdapter = getAdapter()

        getList().apply {
            listAdapter.apply {
                adapter = this
                adapter = withLoadStateHeaderAndFooter(
                    header = UserLoadStateAdapter { retry() },
                    footer = UserLoadStateAdapter { retry() }
                )
            }
            setHasFixedSize(true)
        }
    }

    /**
     * Listens to load state changes.
     */
    private fun addLoadStateChangeListener() {
        listAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                // Showing list when success
                is LoadState.NotLoading -> {
                    when (listAdapter.itemCount) {
                        0 -> showError(getString(getErrorMessage()))
                        else -> hideProgressbarOrError()
                    }
                    notLoading()
                }
                // Showing progress for load
                is LoadState.Loading -> showProgressbar()
                is LoadState.Error -> {
                    if ((loadState.source.refresh as LoadState.Error).error is NetworkException) {
                        showError(getString(R.string.no_internet_connection))
                    } else {
                        showError(getString(getErrorMessage()))
                    }
                }
            }

            // Popping error
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let { showMessage("\uD83D\uDE28 Error ${it.error}") }
        }
    }

    abstract fun getAdapter(): BasePagingAdapter<T, VH>

    abstract fun observeData()

    abstract fun getErrorMessage(): Int

    abstract fun getList(): RecyclerView

    abstract fun notLoading()

    override fun onDestroyView() {
        getList().adapter = null
        super.onDestroyView()
    }
}