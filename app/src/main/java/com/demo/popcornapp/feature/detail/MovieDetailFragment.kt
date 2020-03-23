package com.demo.popcornapp.feature.detail

import android.os.Bundle
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.demo.popcornapp.MovieDetailFragmentBinding
import com.demo.popcornapp.PopCornFragment
import com.demo.popcornapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailFragment : PopCornFragment<MovieDetailFragmentBinding>(R.layout.fragment_detail) {

    private val args: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModel { parametersOf(args.movie) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding().viewModel = viewModel

        requireBinding().back.setOnClickListener { findNavController().navigateUp() }

        requireBinding().currentRating.text = buildSpannedString {
            bold { append(viewModel.movie.voteAvg.toString()) }
            append(getString(R.string.rating_from_ten))
            appendln()
            append(viewModel.movie.voteCount.toString())
        }
    }
}