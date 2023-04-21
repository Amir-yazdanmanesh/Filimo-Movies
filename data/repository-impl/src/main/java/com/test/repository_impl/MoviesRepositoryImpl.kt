package com.test.repository_impl


import com.test.MoviesRepository
import com.test.common_entity.MoviesList
import com.test.common_jvm.ResultWrapper
import com.test.network.networkWrapper.NetworkWrapper
import com.test.network.service.MovieService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MoviesRepository, NetworkWrapper() {

    override suspend fun getMovies(query: String): ResultWrapper<MoviesList?> = getSafe(
        remoteFetch = {
            movieService.getMovies(query)
        }
    )
}