package com.example.movies

import android.content.Context
import android.content.Intent
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
        if (savedInstanceState == null) {
            launchMoviesListFragment()
        }
    }

    private fun launchMoviesListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_saved_movies,
                FavoriteMoviesListFragment()
            )
            .commit()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FavoriteMoviesActivity::class.java)
        }
    }
}