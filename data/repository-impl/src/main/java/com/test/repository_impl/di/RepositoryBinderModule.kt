package com.test.repository_impl.di


import com.test.MoviesRepository
import com.test.repository_impl.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBinderModule {

    @Binds
    abstract fun bindMovieRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

}