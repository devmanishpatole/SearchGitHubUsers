package com.devmanishpatole.githubusers.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.devmanishpatole.githubusers.R
import com.devmanishpatole.githubusers.adapter.UserPagerAdapter
import com.devmanishpatole.githubusers.base.BaseFragment
import com.devmanishpatole.githubusers.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModels()

    private val args by navArgs<DetailFragmentArgs>()

    override fun getLayoutId() = R.layout.fragment_detail

    override fun setupView(view: View, savedInstanceState: Bundle?) {
        pager.adapter = UserPagerAdapter(requireActivity(), args.githubUser)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text =
                if (position == 0) getString(R.string.repositories) else getString(R.string.followers)
        }.attach()
    }

}