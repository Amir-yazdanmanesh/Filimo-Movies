package com.test.filimo.di

import android.app.Application

object AppComponentProvider {
    private lateinit var appComponent: AppComponent

    @JvmStatic
    fun appComponent() = appComponent

    fun provideAppComponent(application: Application) {
        appComponent = DaggerAppComponent.factory().create(application)
    }
}