package com.test.common_entity

data class MoviesList(
    private val data: List<Movie>? = emptyList(),
    private val meta: Meta? = null
) {
    val movies: List<Movie>?
        get() = if (meta != null) {
            if (meta.title_en == "search" && data!!.size == 1) emptyList()
            else throw IllegalStateException("Failed to set movies. Meta is null and data is empty.")
        } else data
}
