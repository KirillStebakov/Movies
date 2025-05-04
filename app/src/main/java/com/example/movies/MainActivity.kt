package com.example.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        launchMoviesListFragment()
    }
    private fun launchMoviesListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_movie_detail,
                MoviesListFragment()
            )
            .commit()
    }
}