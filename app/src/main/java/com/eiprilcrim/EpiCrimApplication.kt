package com.eiprilcrim

import android.app.Application
import com.eiprilcrim.di.AppContainer

class EpiCrimApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
