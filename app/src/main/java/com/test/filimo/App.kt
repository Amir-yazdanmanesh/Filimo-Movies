package com.test.filimo

import androidx.multidex.MultiDexApplication
import com.test.filimo.di.AppComponentProvider
import com.test.filimo.di.SubComponents


class App : MultiDexApplication(), SubComponents {
    override fun onCreate() {
        super.onCreate()
        AppComponentProvider.provideAppComponent(this)
    }
}