package com.demo.popcornapp.feature.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.demo.popcornapp.HomeFragmentBinding
import com.demo.popcornapp.PopCornFragment
import com.demo.popcornapp.R
import com.demo.popcornapp.feature.uimodel.toUiModel
import com.demo.popcornapp.utils.Event
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

        viewModel.event.observe(viewLifecycleOwner, ::handleViewModelEvent)

        requireBinding().apply {
            searchInput.hint = getSearchHint()

            searchInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    this@HomeFragment.viewModel.performSearch()
                    true
                } else {
                    false
                }
            }

            searchInput.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    requireBinding().searchInput.showDropDown()
                }
            }
        }

        val suggestionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mutableListOf<String>())
        viewModel.suggestions.observe(viewLifecycleOwner) {
            suggestionAdapter.clear()
            suggestionAdapter.addAll(it)
            suggestionAdapter.notifyDataSetChanged()
        }

        requireBinding().searchInput.setAdapter(suggestionAdapter)
    }

    private fun handleViewModelEvent(event: Event<HomeViewModel.VMEvent>) {
        when (val e = event.consume()) {
            HomeViewModel.VMEvent.ShowSearchQueryRule ->
                Snackbar.make(requireBinding().root, R.string.home_min_length_for_search_warning, Snackbar.LENGTH_SHORT).show()
            is HomeViewModel.VMEvent.SearchSucceeded -> {
                requireBinding().root.hideKeyboard()
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeToResult(e.movies.map { it.toUiModel() }.toTypedArray(), viewModel.searchQuery.value.orEmpty())
                )
            }
            is HomeViewModel.VMEvent.SearchFailed -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.unable_to_load_results)
                    .setMessage(e.reasonTextResId)
                    .show()
                Unit
            }
            null -> Unit
        }.exhaustive
    }

    private fun getSearchHint() = buildSpannedString {
        bold { color(requireContext().color(R.color.black)) { append(getString(R.string.find)) } }
        append(" ")
        append(getString(R.string.the_movie))
    }
}