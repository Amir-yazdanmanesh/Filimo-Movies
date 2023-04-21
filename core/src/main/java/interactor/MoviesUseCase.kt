package interactor

import com.test.repository.MoviesRepository
import javax.inject.Inject

open class MoviesUseCase @Inject constructor(
    private val movieRepository: MoviesRepository
) {
    suspend fun getMovies(query:String) = movieRepository.getMovies(query)
}