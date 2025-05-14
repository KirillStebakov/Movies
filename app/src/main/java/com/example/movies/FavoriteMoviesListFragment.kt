package com.example.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapters.adapterMovies.MovieInfoAdapter
import com.example.movies.databinding.FragmentMoviesListBinding
import com.example.movies.viewModels.FavoriteMoviesViewModel
import com.example.movies.viewModels.ViewModelFactory
import javax.inject.Inject

class FavoriteMoviesListFragment : BaseFragment<FragmentMoviesListBinding>
    (FragmentMoviesListBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FavoriteMoviesViewModel
    private lateinit var adapter: MovieInfoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    override fun performInjection() {
        component.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[FavoriteMoviesViewModel::class.java]
        setupRecyclers()
        adapter.onMovieClickListener = {
            launchMovieDetailFragment(it.id)
        }
        viewModel.favoritesList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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
                R.id.fragment_container_saved_movies,
                FavoriteMovieDetailFragment.newInstance(id)
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