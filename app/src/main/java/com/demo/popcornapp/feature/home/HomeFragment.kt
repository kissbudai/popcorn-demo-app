package com.demo.popcornapp.feature.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.demo.popcornapp.HomeFragmentBinding
import com.demo.popcornapp.PopCornFragment
import com.demo.popcornapp.R
import com.demo.popcornapp.feature.uimodel.toUiModel
import com.demo.popcornapp.utils.extensions.color
import com.demo.popcornapp.utils.extensions.exhaustive
import com.demo.popcornapp.utils.extensions.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : PopCornFragment<HomeFragmentBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding().viewModel = viewModel

        requireBinding().searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.performSearch()
                true
            } else {
                false
            }
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (val e = event.consume()) {
                HomeViewModel.VMEvent.ShowSearchQueryRule ->
                    Snackbar.make(requireBinding().root, R.string.home_min_length_for_search_warning, Snackbar.LENGTH_SHORT).show()
                is HomeViewModel.VMEvent.SearchSucceeded -> {
                    requireBinding().root.hideKeyboard()
                    Toast.makeText(requireContext(), "Data received, TODO: navigate to result, WIP", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToResult(e.movies.map { it.toUiModel() }.toTypedArray(), viewModel.searchQuery.value.orEmpty())
                    )
                }
                is HomeViewModel.VMEvent.SearchFailed ->
                    Snackbar.make(requireBinding().root, e.reasonTextResId, Snackbar.LENGTH_SHORT).show()
                null -> Unit
            }.exhaustive
        }

        val searchHint = buildSpannedString {

            bold { color(requireContext().color(R.color.black)) { append(getString(R.string.find)) } }
            append(" ")
            append(getString(R.string.the_movie))
        }

        requireBinding().searchInput.hint = searchHint
    }
}