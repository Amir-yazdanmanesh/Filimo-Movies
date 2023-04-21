package com.test.movies


import com.test.common_entity.Movie
import com.test.common_entity.MoviesList
import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState
import com.test.movies.contract.redux.MoviesMiddleware
import com.test.movies.contract.redux.MoviesStore
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @Mock
    private lateinit var mockStore: MoviesStore

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        // set up test dispatcher to be used by coroutines
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MoviesViewModel(mockStore)
    }

    @After
    fun tearDown() {
        // reset dispatcher after test is done
        Dispatchers.resetMain()
    }

    @Test
    fun `getMoviesEvent should dispatch GetMovies action`() {
        runBlocking {
            // arrange
            val query = "Avengers"

            // act
            viewModel.getMoviesEvent(query)

            // assert
            verify(mockStore).dispatch(MoviesAction.GetMovies(query))
        }
    }

    @Test
    fun `initial state should be correctly initialized`() {
        runBlocking {
            // arrange
            val currentState = MoviesState.initialize()
            val moviesMiddleware = MoviesMiddleware(mock())
            val store = MoviesStore(moviesMiddleware)

            // assert
            assertEquals(currentState, store.state.value)
        }
    }

    @Test
    fun `reducer should update state for ShowLoading action`() {
        runBlocking {
            // arrange
            val currentState = MoviesState.initialize()
            val expectedState = currentState.copy(isLoading = true)
            val moviesMiddleware = MoviesMiddleware(mock())
            val store = MoviesStore(moviesMiddleware)

            // act
            store.dispatch(MoviesAction.ShowLoading)

            // assert
            assertEquals(expectedState, store.state.value)

        }
    }

    @Test
    fun `reducer should update state for GetMoviesLoaded action`() {
        runBlocking {
            // arrange
            val movieList = MoviesList(mutableListOf(Movie(1)), null)
            val query = "Avengers"
            val action = MoviesAction.GetMoviesLoaded(movieList, query)
            val expectedState = MoviesState(
                movies = movieList.movies.orEmpty(),
                isLoading = false,
                isSearchResultEmpty = movieList.movies.isNullOrEmpty(),
                isListMoviesEmpty = movieList.movies.isNullOrEmpty()
            )

            val moviesMiddleware = MoviesMiddleware(mock())
            val store = MoviesStore(moviesMiddleware)

            // act
            store.dispatch(action)

            // assert
            assertEquals(expectedState, store.state.value)
        }
    }
}
