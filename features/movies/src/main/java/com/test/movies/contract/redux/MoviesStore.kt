package com.test.movies.contract.redux

import com.test.core.base.BaseStore
import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState
import javax.inject.Inject

open class MoviesStore @Inject constructor(
    moviesMiddleware: MoviesMiddleware
): BaseStore<MoviesState, MoviesAction>(
    initialState = MoviesState.initialize(),
    reducer = MoviesReducer(),
    middlewares = listOf(
        moviesMiddleware
    )
)
