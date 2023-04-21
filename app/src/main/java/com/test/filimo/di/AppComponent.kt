package com.test.filimo.di

import android.app.Application
import com.test.core.base.ViewModelFactoryBinderModule
import com.test.movies.di.MoviesComponent
import com.test.network.di.RetrofitModule
import com.test.repository_impl.di.RepositoryBinderModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        RepositoryBinderModule::class,
        SubComponentsModule::class,
        ViewModelFactoryBinderModule::class
    ]
)
interface AppComponent {

    // Save the reference of factories in the app component for creating sub components
    fun moviesComponent(): MoviesComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}