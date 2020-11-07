package com.devmanishpatole.githubusers.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Generates lifecycle events based on view's lifecycle in recycler view.
 * To achieve lifecycle aware view holder class and we can utilise android architectural components.
 * At the same time observes the parents lifecycle events and propagates those to recyclers view's children.
 */
abstract class BasePagingAdapter<T : Any, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>>(
    parentLifecycle: Lifecycle,
    comparator: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, VH>(comparator) {


    init {
        parentLifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onParentDestroy() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            if (getChildViewHolder(it) is BaseItemViewHolder<*, *>) {
                                (getChildViewHolder(it) as BaseItemViewHolder<*, *>).run {
                                    onDestroy()
                                    viewModel.onManualClear()
                                }
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onParentStop() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            if (getChildViewHolder(it) is BaseItemViewHolder<*, *>) {
                                (getChildViewHolder(it) as BaseItemViewHolder<*, *>).onStop()
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onParentStart() {
                recyclerView?.run {
                    if (layoutManager is LinearLayoutManager) {
                        val layoutManager = layoutManager as LinearLayoutManager
                        val first = layoutManager.findFirstVisibleItemPosition()
                        val last = layoutManager.findLastVisibleItemPosition()

                        if (first in 0..last) {
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    if (it is BaseItemViewHolder<*, *>) {
                                        it.onStart()
                                    }
                                }
                            }
                        }
                    }
                }
            }

        })
    }

    private var recyclerView: RecyclerView? = null

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onStop()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}