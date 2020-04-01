package com.vwxyz.imagelaoder

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication :  Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        bind() from singleton { NetworkConnectionIntercepter(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from provider { PhotosViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}