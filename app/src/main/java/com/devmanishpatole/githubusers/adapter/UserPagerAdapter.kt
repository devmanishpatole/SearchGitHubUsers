package com.devmanishpatole.githubusers.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devmanishpatole.githubusers.model.GitHubUser
import com.devmanishpatole.githubusers.ui.FollowersFragment
import com.devmanishpatole.githubusers.ui.RepositoriesFragment

/**
 * Adapter for view pager
 */
class UserPagerAdapter(fa: FragmentActivity, private val user: GitHubUser) :
    FragmentStateAdapter(fa) {

    companion object {
        private const val PAGES = 2
    }

    override fun getItemCount() = PAGES

    override fun createFragment(position: Int) =
        if (position == 0) RepositoriesFragment.newInstance(user.name) else FollowersFragment.newInstance(
            user.name
        )
}