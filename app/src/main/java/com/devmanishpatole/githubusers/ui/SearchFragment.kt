package com.devmanishpatole.githubusers.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.adapter.UserListAdapter
import com.devmanishpatole.githubusers.base.PagingFragment
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.util.hide
import com.devmanishpatole.githubusers.util.navigateWithAnim
import com.devmanishpatole.githubusers.util.show
import com.devmanishpatole.githubusers.viewholder.UserViewHolder
import com.devmanishpatole.githubusers.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.list.*

@AndroidEntryPoint
class SearchFragment : PagingFragment<SearchViewModel, GitHubUser, UserViewHolder>() {

    override val viewModel by viewModels<SearchViewModel>()

    override fun getLayoutId() = R.layout.fragment_search

    private var shouldShowWelcome = true

    companion object {
        private const val WELCOME_STATE = "WELCOME_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shouldShowWelcome = savedInstanceState?.getBoolean(WELCOME_STATE, false) ?: true
    }

    override fun initList() {
        super.initList()
        (listAdapter as UserListAdapter).onItemClick = {
            findNavController().navigateWithAnim(
                SearchFragmentDirections.actionSearchFragmentToDetailFragment(it.name, it)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true) {
                    shouldShowWelcome = false
                    intro.hide()
                    list.show()
                    searchView.clearFocus()
                    viewModel.searchUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun getAdapter() = UserListAdapter(viewLifecycleOwner.lifecycle)

    override fun observeData() {
        viewModel.users.observe(viewLifecycleOwner) {
            listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun getErrorMessage() = R.string.no_user_found

    override fun getList(): RecyclerView = list

    override fun notLoading() {
        if (shouldShowWelcome) {
            hideProgressbarOrError()
            intro.show()
            list.hide()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(WELCOME_STATE, shouldShowWelcome)
    }

}