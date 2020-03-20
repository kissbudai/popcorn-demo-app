package com.demo.popcornapp.feature.home

import android.os.Bundle
import android.view.View
import com.demo.popcornapp.HomeFragmentBinding
import com.demo.popcornapp.R
import com.demo.popcornapp.PopCornFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : PopCornFragment<HomeFragmentBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding().viewModel = viewModel
    }
}