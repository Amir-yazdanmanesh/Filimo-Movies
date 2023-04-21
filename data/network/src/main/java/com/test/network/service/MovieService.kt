package com.test.network.service


import com.test.common_entity.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {
    @GET("text/{query}/sug/on")
    suspend fun getMovies(@Path("query") query: String): Response<MoviesList?>
}