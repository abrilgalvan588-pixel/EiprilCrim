package com.eiprilcrim.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eiprilcrim.data.dao.UsuarioDao
import com.eiprilcrim.data.entity.UsuarioEntity

@Database(
    entities = [
        UsuarioEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
}
