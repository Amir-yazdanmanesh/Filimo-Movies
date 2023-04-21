package com.test.filimo.di

import com.test.movies.di.MoviesComponent
import dagger.Module

@Module(
    subcomponents = [
        MoviesComponent::class,
    ]
)
class SubComponentsModule