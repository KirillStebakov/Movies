package com.example.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapter.MovieInfoAdapter
import com.example.movies.adapter.addOnEndlessScrollListener
import com.example.movies.databinding.FragmentMoviesListBinding
import com.example.movies.viewModels.MoviesViewModel
import com.example.movies.viewModels.State
import kotlinx.coroutines.launch

class MoviesListFragment :
    BaseFragment<FragmentMoviesListBinding>(FragmentMoviesListBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MoviesViewModel::class.java]
    }
    private lateinit var adapter: MovieInfoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieInfoAdapter(requireActivity())
        binding.rvMovieList.adapter = adapter
        gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvMovieList.layoutManager = gridLayoutManager
        adapter.onMovieClickListener = {
            launchMovieDetailFragment(it.id)
        }

        Log.d("Main", "$viewModel")

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.movies.collect {
                    when (it) {
                        is State.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is State.Content -> {
                            binding.progressBar.isGone = true
                            adapter.submitList(it.movieList)
                        }
                    }

                }
            }
        }
         binding.rvMovieList.addOnEndlessScrollListener(gridLayoutManager) {
             viewModel.loadMovieList()
         }
    }

    private fun launchMovieDetailFragment(id: Int?) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_movie_detail,
                MovieDetailFragment.newInstance(id)
            )
            .addToBackStack(null)
            .commit()
    }
}