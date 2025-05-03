package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivityFavoriteMoviesBinding

class FavoriteMoviesActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFavoriteMoviesBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}