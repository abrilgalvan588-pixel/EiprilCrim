package com.eiprilcrim.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eiprilcrim.data.entity.ResultadoQuizEntity

@Dao
interface ResultadoQuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(resultado: ResultadoQuizEntity)

    @Query("SELECT * FROM resultados_quiz WHERE idUsuario = :idUsuario ORDER BY id DESC LIMIT 1")
    suspend fun obtenerUltimoPorUsuario(idUsuario: Int): ResultadoQuizEntity?

    @Query("SELECT * FROM resultados_quiz WHERE idUsuario = :idUsuario ORDER BY id DESC")
    suspend fun obtenerHistorial(idUsuario: Int): List<ResultadoQuizEntity>
}
