package com.test.repository_impl

import com.test.common_entity.MoviesList
import com.test.common_jvm.ErrorEntity
import com.test.common_jvm.ResultWrapper
import com.test.network.service.MovieService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    private lateinit var movieService: MovieService

    private lateinit var movieRepository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        movieRepository = MoviesRepositoryImpl(movieService)
    }

    @Test
    fun `getMovies should return success`() = runBlocking {
        // Mock the response from the movieService
        val movieList = MoviesList(mutableListOf(), null)
        val query = "Hi"
        val response = Response.success(movieList)
        `when`(movieService.getMovies(query)).thenReturn(response)

        // Call the repository method and verify the response
        val result = movieRepository.getMovies(query)
        assertTrue(result is ResultWrapper.Success && result.data == movieList)
    }

    @Test
    fun `getMovies should return error`() = runBlocking {
        // Mock an error response from the movieService
        val errorResponse = Response.error<MoviesList>(
            500,
            "Error body".toResponseBody("application/json".toMediaTypeOrNull())
        )
        `when`(movieService.getMovies("query")).thenThrow(HttpException(errorResponse))

        // Call the repository method and verify the response
        val result = movieRepository.getMovies("query")

        // Create an instance of the expected error based on the error response
        val expectedError =
            ErrorEntity.Api(message = "Response.error()", code = 500, errorBody = "Error body")

        // Verify that the error in the result matches the expected error
        assertEquals(expectedError.message, result.error?.message)
        assertEquals(expectedError.code, result.error?.code)
        assertEquals(expectedError.errorBody, result.error?.errorBody)
    }


}
