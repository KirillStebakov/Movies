package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapters.adapterMovies.MovieInfoAdapter
import com.example.movies.databinding.FragmentMoviesListBinding
import com.example.movies.viewModels.FavoriteMoviesViewModel

class FavoriteMoviesListFragment : BaseFragment<FragmentMoviesListBinding>
    (FragmentMoviesListBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[FavoriteMoviesViewModel::class.java]
    }
    private lateinit var adapter: MovieInfoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclers()
        adapter.onMovieClickListener = {
            launchMovieDetailFragment(it.id)
        }
        viewModel.favoritesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
    private fun setupRecyclers(){
        adapter = MovieInfoAdapter()
        binding.rvMovieList.adapter = adapter
        gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvMovieList.layoutManager = gridLayoutManager
    }

    private fun launchMovieDetailFragment(id: Int?) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_saved_movies,
                FavoriteMovieDetailFragment.newInstance(id)
            )
            .addToBackStack(null)
            .commit()
    }
}