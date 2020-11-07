package com.devmanishpatole.githubusers.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.service.GlideApp

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun NavController.navigateWithAnim(directions: NavDirections) {
    try {
        this.navigate(
            directions.actionId,
            directions.arguments,
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_anim_in)
                .setExitAnim(R.anim.slide_anim_out)
                .setPopEnterAnim(R.anim.slide_pop_anim_in)
                .setPopExitAnim(R.anim.slide_pop_anim_out).build()
        )
    } catch (e: IllegalArgumentException) {
        Log.e("NavController", "Multiple navigation attempts handled ${e.message}")
    }
}

fun ImageView.load(
    url: String,
    placeholder: Int = R.drawable.placeholder,
    error: Int = R.drawable.image_not_available
) {
    GlideApp
        .with(this)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .error(error)
        .into(this)
}


