package com.devmanishpatole.githubusers.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.util.ViewUtil.hideView
import com.devmanishpatole.githubusers.util.ViewUtil.showView
import kotlinx.android.synthetic.main.base_layout.*
import kotlinx.android.synthetic.main.base_layout.view.*

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, {
            it.data?.run { showMessage(this) }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.base_layout, container, false)
        val content = inflater.inflate(getLayoutId(), container, false)
        rootView.mainViewContainer.addView(content)
        return rootView
    }

    protected fun showProgressbar() {
        hideView(mainViewContainer, progressText, errorText)
        showView(progressbar)
    }

    protected fun hideProgressbarOrError() {
        showView(mainViewContainer)
        hideView(progressText, errorText, progressbar)
    }

    protected fun showProgressbarWithText(text: String) {
        progressText.text = text
        hideView(mainViewContainer)
        showView(progressText, progressbar)
    }

    protected fun showError(text: String) {
        errorText.text = text
        showView(errorText)
        hideView(mainViewContainer, progressText, progressbar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupView(view, savedInstanceState)
    }

    fun showMessage(message: String) =
        context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun setupView(view: View, savedInstanceState: Bundle?)
}