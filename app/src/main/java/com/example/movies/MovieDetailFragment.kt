package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.movies.databinding.FragmentMovieDetailBinding
import com.example.movies.viewModels.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = parseArgs()
        val viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        val job = lifecycleScope.async {
            viewModel.getMovieInfoUseCase(id)
        }
        lifecycleScope.launch {
            val data = job.await()
            with(binding) {
                with(data) {
                    Picasso.get().load(this?.poster?.url).into(ivPoster)
                    tvName.text = this?.name
                }
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
                    id?.let { putInt(ARGS,it) }
                }
            }
    }
}