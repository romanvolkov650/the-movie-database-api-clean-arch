package com.romanvolkov.themovie.presentation.ui

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.romanvolkov.themovie.R
import com.romanvolkov.themovie.core.common.Constants.IMAGE_URL_ORIGINAL
import com.romanvolkov.themovie.databinding.FragmentMovieDetailsBinding
import com.romanvolkov.themovie.presentation.model.MovieState
import com.romanvolkov.themovie.presentation.viewmodel.MainViewModel
import com.romanvolkov.themovie.presentation.viewmodel.MovieActions

class MovieDetailsFragment : DialogFragment(R.layout.fragment_movie_details) {

    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val model: MainViewModel by activityViewModels()

    private var movieId: Int = 0

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt(MOVIE_ID, movieId)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        model.dispatch { MovieActions.GetMovieDetails(movieId) }
        model.dispatch { MovieActions.GetTrailer(movieId) }

        binding.llWatch.setOnClickListener {
            val url = model.movieDetailState.value.trailerUrl
            if (url.isNotEmpty()) {
                startActivity(Intent(ACTION_VIEW, Uri.parse(url)))
            }
        }

        model.movieDetailState.observe(this) {
            render(it)
        }
    }

    private fun render(state: MovieState) {
        binding.pbLoading.isVisible = state.isLoading
        binding.scrollContainer.isGone = state.isLoading
        binding.apply {
            state.imagePath?.let { imagePath ->
                Glide.with(ivPoster)
                    .load("$IMAGE_URL_ORIGINAL$imagePath")
                    .placeholder(R.drawable.ic_logo)
                    .into(ivPoster)
            }
            tvName.text = state.name
            fillProgress.setPercent(state.rating)
            if (state.tagline?.isNotEmpty() == true) {
                tvTagline.isVisible = true
                tvTagline.text = state.tagline
            } else {
                tvTagline.isGone = true
            }
            llWatch.isVisible = state.trailerUrl.isNotEmpty()
            tvDescription.text = state.description
        }
    }

    private fun getArgs() {
        movieId = arguments?.getInt(MOVIE_ID) ?: 0
    }
}