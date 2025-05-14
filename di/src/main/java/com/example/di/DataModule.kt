package com.example.di

import android.app.Application
import com.example.data.db.AppDatabase
import com.example.data.db.MovieInfoDao
import com.example.data.dto.ApiFactory
import com.example.data.dto.ApiService
import com.example.data.repository.RepositoryMovieImpl
import com.example.domain.RepositoryMovie
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindMovieRepository(impl: RepositoryMovieImpl): RepositoryMovie

    companion object {
        @ApplicationScope
        @Provides
        fun provideMovieInfoDao(application: Application): MovieInfoDao {
            return AppDatabase.getInstance(application).movieInfoDao()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}