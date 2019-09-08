package com.example.twgproducttask

import android.app.Application
import com.campgladiator.injection.AndroidModule
import com.campgladiator.injection.NetworkModule
import com.example.twgproducttask.injection.ApplicationComponent
import com.example.twgproducttask.injection.DaggerApplicationComponent

class TWGApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()

    }

    private fun setupDagger() {
        graph = DaggerApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .networkModule(NetworkModule(this))
            .build()
        graph.inject(this)
    }
}