package com.test.movies.contract.redux

import com.test.core.base.Reducer
import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState


class MoviesReducer : Reducer<MoviesState, MoviesAction> {

    override fun reduce(currentState: MoviesState, action: MoviesAction): MoviesState {
        return when (action) {
            is MoviesAction.ShowLoading -> {
                currentState.copy(isLoading = true)
            }

            is MoviesAction.HideLoading -> {
                currentState.copy(isLoading = false)
            }

            is MoviesAction.GetMoviesLoaded -> {
                // movies have not to be null
                val movies = action.movieList.movies!!
                currentState.copy(
                    movies = movies,
                    isSearchResultEmpty = (movies.isEmpty() && action.query.isNotEmpty()),
                    isListMoviesEmpty = movies.isEmpty()
                )

            }
            // return current state
            else -> currentState
        }
    }
}