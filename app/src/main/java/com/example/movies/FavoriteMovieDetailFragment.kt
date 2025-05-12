package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entity.movieInfo.MovieInfo
import com.example.movies.databinding.FragmentFavoriteMovieDetailBinding
import com.example.movies.viewModels.FavoriteMoviesViewModel
import com.squareup.picasso.Picasso

class FavoriteMovieDetailFragment : BaseFragment<FragmentFavoriteMovieDetailBinding>
    (FragmentFavoriteMovieDetailBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[FavoriteMoviesViewModel::class.java]
    }
    private var movieInfo: MovieInfo? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = parseArgs()
        viewModel.isInvoked.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.fetchDetails(id)
            }
        }
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            with(binding) {
                with(it) {
                    Picasso.get().load(this?.poster?.url).into(ivPoster)
                    tvName.text = this?.name ?: this?.alternativeName
                    tvYear.text = this?.year.toString()
                    tvDescription.text = this?.description
                }
            }
            movieInfo = it
        }
        viewModel.isFavorite(id).observe(viewLifecycleOwner) { state ->
            if (state) {
                binding.imageViewStar.setOnClickListener { view ->
                    viewModel.removeFromFavorites(movieInfo)
                }
                binding.imageViewStar.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        android.R.drawable.star_big_on
                    )
                )
            } else {
                binding.imageViewStar.setOnClickListener { view ->
                    viewModel.addToFavorites(movieInfo)
                }
                binding.imageViewStar.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        android.R.drawable.star_big_off
                    )
                )
            }
        }
    }

    private fun parseArgs(): Int {
        return requireArguments().getInt(ARG_PARAM, 0)
    }

    companion object {
        private const val ARG_PARAM = "ARG_PARAM1"
        fun newInstance(id: Int?) =
            FavoriteMovieDetailFragment().apply {
                arguments = Bundle().apply {
                    id?.let { putInt(ARG_PARAM, id) }
                }
            }
    }
}