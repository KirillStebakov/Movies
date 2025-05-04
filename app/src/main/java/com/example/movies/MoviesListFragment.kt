package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptoapp.presentation.adapters.MovieInfoAdapter
import com.example.movies.databinding.FragmentMoviesListBinding
import com.example.movies.viewModels.MovieViewModelFactory
import com.example.movies.viewModels.MoviesViewModel
import com.example.movies.viewModels.State
import kotlinx.coroutines.launch

class MoviesListFragment :
    BaseFragment<FragmentMoviesListBinding>(FragmentMoviesListBinding::inflate) {
    private var page = 1
    private val viewModelFactory by lazy {
        MovieViewModelFactory()
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            page = savedInstanceState.getInt(PAGINATION_KEY, 0)
        }
        val adapter = MovieInfoAdapter(requireActivity())
        binding.rvMovieList.adapter = adapter
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvMovieList.layoutManager = gridLayoutManager
        adapter.onMovieClickListener = {
            launchMovieDetailFragment(it.id)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.movies.collect {
                    when (it) {
                        is State.Initial -> {

                        }

                        is State.Loading -> {

                        }

                        is State.Content -> {

                            adapter.submitList(it.movieList)
                        }
                    }
                }
            }
        }
        /* binding.rvMovieList.addOnEndlessScrollListener(gridLayoutManager) {
             viewModel.loadMovieList()
         }*/
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val myNumber = page
        outState.putInt(PAGINATION_KEY, myNumber)
    }

    companion object {
        private const val PAGINATION_KEY = "PaginationKey"
    }
}