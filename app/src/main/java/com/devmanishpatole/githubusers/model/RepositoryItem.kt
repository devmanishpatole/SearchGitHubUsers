package com.devmanishpatole.githubusers.model


import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("default_branch")
    val defaultBranch: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("labels_url")
    val labelsUrl: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: GitHubUser,
    @SerializedName("private")
    val isPrivate: Boolean,
    @SerializedName("updated_at")
    val updatedAt: String
)