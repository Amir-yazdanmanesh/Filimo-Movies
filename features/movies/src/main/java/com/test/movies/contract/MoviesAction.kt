package com.test.movies.contract

import com.test.common_entity.MoviesList
import com.test.core.base.Action

sealed class MoviesAction : Action {

    data class GetMovies(val query: String) : MoviesAction()
    data class GetMoviesLoaded(val movieList: MoviesList, val query: String) : MoviesAction()

    object ShowLoading : MoviesAction()
    object HideLoading : MoviesAction()

}