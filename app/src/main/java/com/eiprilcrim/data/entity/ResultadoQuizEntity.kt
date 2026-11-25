package com.eiprilcrim.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resultados_quiz")
data class ResultadoQuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idUsuario: Int = 0,
    val correctas: Int,
    val total: Int,
    val porcentaje: Int,
    val fecha: String
)
