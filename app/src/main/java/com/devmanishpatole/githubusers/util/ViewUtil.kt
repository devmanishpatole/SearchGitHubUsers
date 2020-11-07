package com.devmanishpatole.githubusers.util

import android.view.View

object ViewUtil {

    fun hideView(vararg views: View) {
        for (view in views) {
            view.hide()
        }
    }

    fun showView(vararg views: View) {
        for (view in views) {
            view.show()
        }
    }
}