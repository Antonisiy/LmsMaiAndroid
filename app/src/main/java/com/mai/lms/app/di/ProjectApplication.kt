package com.mai.lms.app.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            val modules = listOf(
                provideNet(),
                provideDomain(),
                provideUi(),

            )
            modules(modules)
            androidContext(this@ProjectApplication)
        }
    }
}