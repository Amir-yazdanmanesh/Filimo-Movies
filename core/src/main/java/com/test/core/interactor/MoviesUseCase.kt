package com.test.core.interactor

import com.test.MoviesRepository
import javax.inject.Inject

open class MoviesUseCase @Inject constructor(
    private val movieRepository: MoviesRepository
) {
    suspend fun getMovies(query:String) = movieRepository.getMovies(query)
}