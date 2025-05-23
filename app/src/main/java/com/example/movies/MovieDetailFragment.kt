package com.example.movies

import android.content.Intent
import android.os.Bundle
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
import com.example.movies.viewModels.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MoviesViewModel
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    override fun performInjection() {
        component.inject(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MoviesViewModel::class.java]
        val id = parseArgs()
        setupRecyclers()
        val data = viewModel.getMovieInfo(id)
        if (savedInstanceState != null) page = savedInstanceState.getInt(KEY) else viewModel.loadReviewList(id,page++)
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
                viewModel.reviews.collectLatest {
                    reviewAdapter.submitList(it)
                }
            }
        }
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _, scrollY, _, oldScrollY ->
            val child = v.getChildAt(v.childCount - 1) ?: return@setOnScrollChangeListener
            if (scrollY >= (child.measuredHeight - v.measuredHeight) && scrollY > oldScrollY) {
                viewModel.loadReviewList(id, page++)
            }
        }
        viewModel.isFavorite(id).observe(viewLifecycleOwner) {
            if (it) {
                binding.imageViewStar.setOnClickListener { view ->
                    viewModel.removeFromFavorites(data)
                }
                binding.imageViewStar.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        android.R.drawable.star_big_on
                    )
                )
            } else {
                binding.imageViewStar.setOnClickListener { view ->
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
        layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.rvReviewList.layoutManager = layoutManager
    }

    private fun parseArgs(): Int {
        return requireArguments().getInt(ARGS, 0)
    }

    companion object {
        private const val ARGS = "ARG1"
        private const val KEY = "KEY"
        fun newInstance(id: Int?) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    id?.let { putInt(ARGS, it) }
                }
            }
    }

    override fun onDestroyView() {
        binding.rvTrailersList.adapter = null
        binding.rvReviewList.adapter = null
        binding.rvReviewList.layoutManager = null
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY, page)
    }
}