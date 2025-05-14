package com.example.movies

import android.app.Application
import com.example.di.ApplicationScope
import com.example.di.DataModule
import com.example.movies.viewModels.ViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: MoviesListFragment)
    fun inject(fragment: MovieDetailFragment)
    fun inject(fragment: FavoriteMoviesListFragment)
    fun inject(fragment: FavoriteMovieDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}