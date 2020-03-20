package com.demo.popcornapp

import android.app.Application
import com.demo.popcornapp.di.createAppModules

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PopCornApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PopCornApp)
            modules(createAppModules())
        }
    }
}