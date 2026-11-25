package com.eiprilcrim.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eiprilcrim.data.entity.GlosarioEntity

@Dao
interface GlosarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(termino: GlosarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(terminos: List<GlosarioEntity>)

    @Query("SELECT * FROM glosario")
    suspend fun obtenerTodos(): List<GlosarioEntity>

    @Query("SELECT * FROM glosario WHERE termino LIKE '%' || :busqueda || '%' OR definicion LIKE '%' || :busqueda || '%'")
    suspend fun buscarTerminos(busqueda: String): List<GlosarioEntity>
}
