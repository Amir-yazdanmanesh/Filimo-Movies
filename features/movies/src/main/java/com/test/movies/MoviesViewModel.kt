package com.test.movies

import androidx.lifecycle.viewModelScope
import com.test.core.base.BaseViewModelRedux
import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState
import com.test.movies.contract.redux.MoviesStore
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val store: MoviesStore,
) : BaseViewModelRedux<MoviesState, MoviesAction>(store) {
    override val initialState: MoviesState = MoviesState.initialize()

    init {
        getMoviesEvent(MoviesState.noSearch)
    }

    fun getMoviesEvent(query: String) = viewModelScope.launch {
        store.dispatch(MoviesAction.GetMovies(query))
    }

}