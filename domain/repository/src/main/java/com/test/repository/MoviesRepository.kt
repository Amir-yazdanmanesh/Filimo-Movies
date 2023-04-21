package com.test.repository

import com.test.common_entity.MoviesList
import com.test.common_jvm.ResultWrapper

interface MoviesRepository {
    suspend fun getMovies(query: String): ResultWrapper<MoviesList?>
}