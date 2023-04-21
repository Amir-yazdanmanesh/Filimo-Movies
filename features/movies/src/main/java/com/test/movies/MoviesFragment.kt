package com.test.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.test.core.base.BaseFragmentRedux
import com.test.filimo.databinding.FragmentMoviesBinding
import com.test.movies.contract.MoviesAction
import com.test.movies.contract.MoviesState

class MoviesFragment :
    BaseFragmentRedux<FragmentMoviesBinding, MoviesState, MoviesAction, MoviesViewModel>() {

    override val viewModel: MoviesViewModel
        get() = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun renderViewState(viewState: MoviesState) {


    }

}