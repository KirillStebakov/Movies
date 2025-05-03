package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMovieDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}