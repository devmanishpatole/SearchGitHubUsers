package com.devmanishpatole.githubusers.viewmodel

import com.devmanishpatole.githubusers.base.BaseItemViewModel
import com.devmanishpatole.githubusers.model.RepositoryItem
import javax.inject.Inject

/**
 * We can perform operations specific to rows in repository list.
 * Each repository row has it's own viewModel to handle business logic.
 * No need to delegate operation to main view model.
 */
class RepositoryItemViewModel @Inject constructor() : BaseItemViewModel<RepositoryItem>() {
}