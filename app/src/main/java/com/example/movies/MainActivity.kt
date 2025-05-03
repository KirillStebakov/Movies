package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.presentation.adapters.MovieInfoAdapter
import com.example.data.repository.RepositoryMovieImpl
import com.example.domain.useCases.GetMovieListUseCase
import com.example.movies.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val repositoryMovie = RepositoryMovieImpl()
        val getMovieListUseCase = GetMovieListUseCase(repositoryMovie)
        val job = lifecycleScope.async {
            getMovieListUseCase()
        }
        val adapter = MovieInfoAdapter(this)
        binding.rvMovieList.adapter = adapter
        lifecycleScope.launch {
            adapter.submitList(
                job.await()
            )
        }
    }
}