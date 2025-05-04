package com.example.movies

import com.example.movies.databinding.FragmentFavoriteMovieDetailBinding

class FavoriteMovieDetailFragment : BaseFragment<FragmentFavoriteMovieDetailBinding>
    (FragmentFavoriteMovieDetailBinding::inflate) {

    /*companion object {
        fun newInstance(movie: MovieInfoDb) =
            FavoriteMovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("ARG_PARAM1", movie)
                }
            }
    }*/
}