package com.test.filimo.di

import com.test.movies.di.MoviesComponent
import com.test.movies.di.MoviesComponentProvider


interface SubComponents : MoviesComponentProvider {

    override fun provideMoviesComponent(): MoviesComponent =
        AppComponentProvider.appComponent().moviesComponent().create()

}