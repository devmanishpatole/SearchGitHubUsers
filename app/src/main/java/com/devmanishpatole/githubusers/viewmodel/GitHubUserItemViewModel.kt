package com.devmanishpatole.githubusers.viewmodel

import com.devmanishpatole.githubusers.base.BaseItemViewModel
import com.devmanishpatole.githubusers.model.GitHubUser
import javax.inject.Inject

/**
 * We can perform operations specific to rows in list
 * Each GitHubUser row has it's own viewModel to handle business logic.
 * No need to delegate operation to main view model.
 */
class GitHubUserItemViewModel @Inject constructor() : BaseItemViewModel<GitHubUser>()