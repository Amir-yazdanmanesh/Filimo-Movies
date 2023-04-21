package com.test.movies.di

import com.test.movies.MoviesFragment
import dagger.Subcomponent
import com.test.core.utils.ViewModelScope

@ViewModelScope
@Subcomponent(modules = [ViewModelModule::class])
interface MoviesComponent {

    fun inject(moviesFragment: MoviesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create() : MoviesComponent
    }

}