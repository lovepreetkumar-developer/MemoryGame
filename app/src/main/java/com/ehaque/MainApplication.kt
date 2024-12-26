package com.ehaque

import android.app.Application
import com.ehaque.data.preferences.PreferenceProvider
import com.ehaque.vm_factories.AppViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this);
    }

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MainApplication))
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AppViewModelFactory() }

    }
}