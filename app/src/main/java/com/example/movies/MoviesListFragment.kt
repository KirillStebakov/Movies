package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapters.adapterMovies.MovieInfoAdapter
import com.example.movies.adapters.addOnEndlessScrollListener
import com.example.movies.databinding.FragmentMoviesListBinding
import com.example.movies.viewModels.MoviesViewModel
import com.example.movies.viewModels.State
import com.example.movies.viewModels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesListFragment :
    BaseFragment<FragmentMoviesListBinding>(FragmentMoviesListBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MovieInfoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    override fun performInjection() {
        component.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MoviesViewModel::class.java]
        setupRecyclers()
        adapter.onMovieClickListener = {
            launchMovieDetailFragment(it.id)
        }
        viewModel.isMoviesInvoked.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.loadMovieList()
            }
        }
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

    private fun setupRecyclers() {
        adapter = MovieInfoAdapter()
        binding.rvMovieList.adapter = adapter
        gridLayoutManager = GridLayoutManager(requireContext().applicationContext, 2)
        binding.rvMovieList.layoutManager = gridLayoutManager
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

    override fun onDestroyView() {
        binding.rvMovieList.adapter = null
        binding.rvMovieList.layoutManager = null
        super.onDestroyView()
    }
}