package com.example.movies

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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
                R.id.fragment_container_movie_detail,
                MoviesListFragment()
            )
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemFavourite) {
            FavoriteMoviesActivity.newIntent(this).apply {
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}