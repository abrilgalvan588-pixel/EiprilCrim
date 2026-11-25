package com.eiprilcrim.di

import android.content.Context

class AppContainer(context: Context) {
    val appModule: AppModule = AppModuleImpl(context)
}
