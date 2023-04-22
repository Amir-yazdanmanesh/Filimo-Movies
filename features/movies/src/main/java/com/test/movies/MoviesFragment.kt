package com.test.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.test.core.base.BaseFragmentRedux
import com.test.core.extension.findComponent
import com.test.core.extension.gone
import com.test.core.extension.onQueryTextListener
import com.test.core.extension.visible
import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState
import com.test.movies.databinding.FragmentMoviesBinding
import com.test.movies.di.MoviesComponentProvider

class MoviesFragment :
    BaseFragmentRedux<FragmentMoviesBinding, MoviesState, MoviesAction, MoviesViewModel>() {

    override val viewModel: MoviesViewModel
        get() = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        findComponent<MoviesComponentProvider>().provideMoviesComponent().inject(this)
        binding.apply {
            searchMovies.onActionViewExpanded()
            recyclerView.setHasFixedSize(true)
            searchMovies.onQueryTextListener {
                viewModel.getMoviesEvent(it)
            }
        }
    }

    override fun renderViewState(viewState: MoviesState) {
        binding.apply {
            if (viewState.isLoading) {
                startShimmer()
                emptyList.gone()
                recyclerView.gone()
            } else {
                stopShimmer()
                recyclerView.visible()
            }

            if (viewState.isSearchResultEmpty) {
                emptyListText.text = getString(R.string.movie_not_found)
            } else {
                emptyListText.text = getString(R.string.empty_list_text)
            }

            if (viewState.isListMoviesEmpty && !viewState.isLoading) {
                emptyList.visible()
            } else
                emptyList.gone()

            recyclerView.withModels {
                viewState.movies.forEach { movie ->
                    movie {
                        id(movie.id)
                        movie(movie)
                        onClickListener { _ ->

                        }
                    }
                }
            }
        }
    }

    fun stopShimmer() {
        binding.moviesShimmer.hideShimmer()
        binding.moviesShimmer.gone()
    }

    fun startShimmer() {
        if (!binding.moviesShimmer.isShimmerStarted) {
            binding.moviesShimmer.showShimmer(true)
            binding.moviesShimmer.visible()
        }
    }
}