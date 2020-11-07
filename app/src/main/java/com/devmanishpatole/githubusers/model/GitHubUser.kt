package com.devmanishpatole.githubusers.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubUser(
	@SerializedName("login") val name: String,
	@SerializedName("id") val id: Int,
	@SerializedName("avatar_url") val imageUrl: String,
	@SerializedName("score") val score: Int
) : Parcelable