package com.eiprilcrim.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eiprilcrim.data.entity.CasoEntity

@Dao
interface CasoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(caso: CasoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(casos: List<CasoEntity>)

    @Query("SELECT * FROM casos")
    suspend fun obtenerTodos(): List<CasoEntity>

    @Query("SELECT * FROM casos WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): CasoEntity?

    @Query("SELECT * FROM casos WHERE (:nivel = 'Todos' OR nivel = :nivel) AND (titulo LIKE '%' || :busqueda || '%' OR descripcion LIKE '%' || :busqueda || '%')")
    suspend fun buscarCasos(busqueda: String, nivel: String): List<CasoEntity>
}
