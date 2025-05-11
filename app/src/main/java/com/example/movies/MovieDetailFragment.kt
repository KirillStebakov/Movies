package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.adapters.adapterReviews.ReviewAdapter
import com.example.movies.adapters.adapterTrailers.TrailersAdapter
import com.example.movies.databinding.FragmentMovieDetailBinding
import com.example.movies.viewModels.MoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MoviesViewModel::class.java]
    }
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = parseArgs()
        setupRecyclers()

        val data = viewModel.getMovieInfo(id)
        viewModel.isReviewInvoked.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.loadReviewList(id)
            }
        }

        trailersAdapter.onTrailerClickListener = {
            val intent = Intent(Intent.ACTION_VIEW, it.url?.toUri())
            startActivity(intent)
        }
        with(binding) {
            with(data) {
                Picasso.get().load(this?.poster?.url).into(ivPoster)
                tvName.text = this?.name ?: this?.alternativeName
                tvYear.text = this?.year.toString()
                tvDescription.text = this?.description
                trailersAdapter.submitList(data?.trailersList)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.reviews.collect {
                    reviewAdapter.submitList(it)
                }
            }
        }
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _, scrollY, _, oldScrollY ->
            val child = v.getChildAt(v.childCount - 1) ?: return@setOnScrollChangeListener
            if (scrollY >= (child.measuredHeight - v.measuredHeight) && scrollY > oldScrollY) {
                viewModel.loadReviewList(id)
            }
        }
        viewModel.fetchMovieDetails(id).observe(viewLifecycleOwner) {
            (Log.d("Main", "$it"))
            if (it?.id != null) {
                binding.imageViewStar.setOnClickListener {view ->
                    viewModel.removeFromFavorites(data)
                }
                binding.imageViewStar.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        android.R.drawable.star_big_on
                    )
                )
            } else {
                binding.imageViewStar.setOnClickListener {view ->
                    viewModel.addToFavorites(data)
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

    private fun setupRecyclers() {
        trailersAdapter = TrailersAdapter()
        binding.rvTrailersList.adapter = trailersAdapter

        reviewAdapter = ReviewAdapter()
        binding.rvReviewList.adapter = reviewAdapter
        layoutManager = LinearLayoutManager(requireActivity())
        binding.rvReviewList.layoutManager = layoutManager
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