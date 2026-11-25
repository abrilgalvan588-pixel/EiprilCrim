package com.eiprilcrim.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eiprilcrim.data.entity.ProtocoloEntity

@Dao
interface ProtocoloDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(protocolo: ProtocoloEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(protocolos: List<ProtocoloEntity>)

    @Query("SELECT * FROM protocolos")
    suspend fun obtenerTodos(): List<ProtocoloEntity>

    @Query("SELECT * FROM protocolos WHERE nombre LIKE '%' || :busqueda || '%' OR descripcion LIKE '%' || :busqueda || '%'")
    suspend fun buscarProtocolos(busqueda: String): List<ProtocoloEntity>
}
