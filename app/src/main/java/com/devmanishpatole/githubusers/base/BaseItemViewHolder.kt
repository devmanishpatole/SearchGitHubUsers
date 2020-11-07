package com.devmanishpatole.githubusers.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItemViewHolder<T, VM : BaseItemViewModel<T>>(
    @LayoutRes layoutId: Int,
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)),
    LifecycleOwner {

    /**
     * To make lifecycle aware view holder we need lifecycleRegistry to generate life cycle
     * events based on view's lifecycle in recycler view
     */
    abstract var lifecycleRegistry: LifecycleRegistry

    /**
     * Requires viewModel to perform row specific operation.
     */
    abstract var viewModel: VM

    init {
        onCreate()
    }

    override fun getLifecycle() = lifecycleRegistry

    open fun bind(data: T) {
        viewModel.updateData(data)
    }

    /**
     * Notifies observer about created lifecycle event.
     */
    private fun onCreate() {
        injectDependency()
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        initObserver()
        setupView(itemView)
    }

    /**
     * Notifies observer about start lifecycle event.
     */
    fun onStart() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    /**
     * Notifies observer about stop lifecycle event.
     */
    fun onStop() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    /**
     * Notifies observer about destroy lifecycle event.
     */
    fun onDestroy() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    protected open fun initObserver() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    private fun showMessage(message: String) =
        Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()

    private fun showMessage(@StringRes resId: Int) = showMessage(itemView.context.getString(resId))

    protected abstract fun setupView(view: View)

    protected abstract fun injectDependency()

}