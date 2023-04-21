package com.test.movies.di

import androidx.lifecycle.ViewModel
import com.test.movies.MoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.test.core.utils.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun firstViewModel(viewModel: MoviesViewModel): ViewModel
}