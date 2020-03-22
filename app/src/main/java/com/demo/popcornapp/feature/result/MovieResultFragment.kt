package com.demo.popcornapp.feature.result

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.popcornapp.MovieResultFragmentBinding
import com.demo.popcornapp.PopCornFragment
import com.demo.popcornapp.R
import com.demo.popcornapp.shared.RecyclerSpaceItemDivider
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieResultFragment : PopCornFragment<MovieResultFragmentBinding>(R.layout.fragment_result) {

    private val args by navArgs<MovieResultFragmentArgs>()
    private val viewModel: MovieResultViewModel by viewModel { parametersOf(args.query, args.movies.toList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding().viewModel = viewModel

        requireBinding().toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val movieAdapter = MovieAdapter()
        movieAdapter.setItemClickListener { position -> }

        viewModel.movieList.observe(viewLifecycleOwner, movieAdapter::submitList)

        requireBinding().recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
            addItemDecoration(RecyclerSpaceItemDivider(resources.getDimensionPixelSize(R.dimen.first_keyline)))
        }
    }
}