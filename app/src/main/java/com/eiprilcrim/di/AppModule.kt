package com.eiprilcrim.di

import android.content.Context
import com.eiprilcrim.data.dao.CasoDao
import com.eiprilcrim.data.dao.GlosarioDao
import com.eiprilcrim.data.dao.ProtocoloDao
import com.eiprilcrim.data.dao.ResultadoQuizDao
import com.eiprilcrim.data.dao.UsuarioDao
import com.eiprilcrim.data.database.AppDatabase
import com.eiprilcrim.data.database.DatabaseProvider
import com.eiprilcrim.data.repository.CasoRepositoryImpl
import com.eiprilcrim.data.repository.GlosarioRepositoryImpl
import com.eiprilcrim.data.repository.ProtocoloRepositoryImpl
import com.eiprilcrim.data.repository.ResultadoQuizRepositoryImpl
import com.eiprilcrim.data.repository.SettingsRepository
import com.eiprilcrim.data.repository.UsuarioRepositoryImpl
import com.eiprilcrim.domain.repository.CasoRepository
import com.eiprilcrim.domain.repository.GlosarioRepository
import com.eiprilcrim.domain.repository.ProtocoloRepository
import com.eiprilcrim.domain.repository.ResultadoQuizRepository
import com.eiprilcrim.domain.repository.UsuarioRepository

interface AppModule {
    val database: AppDatabase
    val usuarioDao: UsuarioDao
    val casoDao: CasoDao
    val glosarioDao: GlosarioDao
    val protocoloDao: ProtocoloDao
    val resultadoQuizDao: ResultadoQuizDao

    val usuarioRepository: UsuarioRepository
    val casoRepository: CasoRepository
    val glosarioRepository: GlosarioRepository
    val protocoloRepository: ProtocoloRepository
    val resultadoQuizRepository: ResultadoQuizRepository
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

    override val casoDao: CasoDao by lazy {
        database.casoDao()
    }

    override val glosarioDao: GlosarioDao by lazy {
        database.glosarioDao()
    }

    override val protocoloDao: ProtocoloDao by lazy {
        database.protocoloDao()
    }

    override val resultadoQuizDao: ResultadoQuizDao by lazy {
        database.resultadoQuizDao()
    }

    override val usuarioRepository: UsuarioRepository by lazy {
        UsuarioRepositoryImpl(usuarioDao)
    }

    override val casoRepository: CasoRepository by lazy {
        CasoRepositoryImpl(casoDao)
    }

    override val glosarioRepository: GlosarioRepository by lazy {
        GlosarioRepositoryImpl(glosarioDao)
    }

    override val protocoloRepository: ProtocoloRepository by lazy {
        ProtocoloRepositoryImpl(protocoloDao)
    }

    override val resultadoQuizRepository: ResultadoQuizRepository by lazy {
        ResultadoQuizRepositoryImpl(resultadoQuizDao)
    }

    override val settingsRepository: SettingsRepository by lazy {
        SettingsRepository(context)
    }
}
