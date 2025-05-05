package com.example.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.movies.databinding.FragmentMovieDetailBinding
import com.example.movies.viewModels.MoviesViewModel
import com.squareup.picasso.Picasso

class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MoviesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = parseArgs()

        Log.d("Main", "$viewModel")

        val data = viewModel.getMovieInfo(id)
        with(binding) {
            with(data) {
                Picasso.get().load(this?.poster?.url).into(ivPoster)
                tvName.text = this?.name ?: this?.alternativeName
                tvYear.text = this?.year.toString()
                tvDescription.text = this?.description
            }
        }
    }

    private fun parseArgs(): Int {
        return requireArguments().getInt(ARGS, 0)
    }

    companion object {
        private const val ARGS = "ARG1"
        fun newInstance(id: Int?) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    id?.let { putInt(ARGS, it) }
                }
            }
    }

}