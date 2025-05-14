package com.example.movies.viewModels

import androidx.lifecycle.ViewModel
import com.example.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    @Binds
    fun bindViewModel(impl: MoviesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavoriteMoviesViewModel::class)
    @Binds
    fun bindFavoriteViewModel(impl: FavoriteMoviesViewModel): ViewModel

}