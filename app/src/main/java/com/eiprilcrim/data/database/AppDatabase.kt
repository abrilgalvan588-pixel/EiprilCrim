package com.eiprilcrim.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eiprilcrim.data.dao.CasoDao
import com.eiprilcrim.data.dao.GlosarioDao
import com.eiprilcrim.data.dao.ProtocoloDao
import com.eiprilcrim.data.dao.ResultadoQuizDao
import com.eiprilcrim.data.dao.UsuarioDao
import com.eiprilcrim.data.entity.CasoEntity
import com.eiprilcrim.data.entity.GlosarioEntity
import com.eiprilcrim.data.entity.ProtocoloEntity
import com.eiprilcrim.data.entity.ResultadoQuizEntity
import com.eiprilcrim.data.entity.UsuarioEntity

@Database(
    entities = [
        UsuarioEntity::class,
        CasoEntity::class,
        GlosarioEntity::class,
        ProtocoloEntity::class,
        ResultadoQuizEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun casoDao(): CasoDao
    abstract fun glosarioDao(): GlosarioDao
    abstract fun protocoloDao(): ProtocoloDao
    abstract fun resultadoQuizDao(): ResultadoQuizDao
}
