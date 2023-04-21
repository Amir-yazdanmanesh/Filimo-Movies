package com.test.movies.contract

import com.test.common_entity.Movie
import com.test.core.base.State

data class MoviesState(
    val movies: List<Movie> = emptyList(),
    val isSearchResultEmpty: Boolean = false,
    val isListMoviesEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
) : State {
    companion object {
        val noSearch = ""
        fun initialize() = MoviesState()
    }
}