package com.example.movies

import android.app.Application

class MovieApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}