package com.test.movies.contract.redux

import com.test.core.base.BaseEffect
import com.test.core.base.BaseMiddleware
import com.test.core.base.Store

import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState
import com.test.core.interactor.MoviesUseCase
import javax.inject.Inject

class MoviesMiddleware @Inject constructor(
    private val movieUseCase: MoviesUseCase
) : BaseMiddleware<MoviesState, MoviesAction>() {


    override suspend fun process(
        action: MoviesAction,
        currentState: MoviesState,
        store: Store<MoviesState, MoviesAction>
    ) {
        when (action) {
            is MoviesAction.GetMovies -> {
                fetchMovies(action.query, store)
            }
            else -> {}
        }
    }

    private suspend fun fetchMovies(query: String, store: Store<MoviesState, MoviesAction>) {
        store.dispatch(MoviesAction.ShowLoading)
        try {
            val movieList = movieUseCase.getMovies(query).manageResult(store)
            store.dispatch(MoviesAction.GetMoviesLoaded(movieList!!, query))
        } catch (exception: Exception) {
            store.setEffect(BaseEffect.ShowToast(exception.message.orEmpty()))
        } finally {
            store.dispatch(MoviesAction.HideLoading)
        }
    }
}