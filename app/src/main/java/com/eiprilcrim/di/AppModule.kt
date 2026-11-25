package com.eiprilcrim.di

import android.content.Context
import com.eiprilcrim.data.dao.UsuarioDao
import com.eiprilcrim.data.database.AppDatabase
import com.eiprilcrim.data.database.DatabaseProvider
import com.eiprilcrim.data.repository.SettingsRepository
import com.eiprilcrim.data.repository.UsuarioRepositoryImpl
import com.eiprilcrim.domain.repository.UsuarioRepository

interface AppModule {
    val database: AppDatabase
    val usuarioDao: UsuarioDao
    val usuarioRepository: UsuarioRepository
    val settingsRepository: SettingsRepository
}

class AppModuleImpl(
    private val context: Context
) : AppModule {

    override val database: AppDatabase by lazy {
        DatabaseProvider.getDatabase(context)
    }

    override val usuarioDao: UsuarioDao by lazy {
        database.usuarioDao()
    }

    override val usuarioRepository: UsuarioRepository by lazy {
        UsuarioRepositoryImpl(usuarioDao)
    }

    override val settingsRepository: SettingsRepository by lazy {
        SettingsRepository(context)
    }
}
