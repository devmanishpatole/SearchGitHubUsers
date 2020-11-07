package com.devmanishpatole.githubusers.model

import com.google.gson.annotations.SerializedName

data class GitHubUserWrapper(
	@SerializedName("total_count") val count: Int,
	@SerializedName("items") val users: List<GitHubUser>
)