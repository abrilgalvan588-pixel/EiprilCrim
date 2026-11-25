package com.eiprilcrim.domain.model

data class ResultadoQuiz(
    val id: Int = 0,
    val idUsuario: Int = 0,
    val correctas: Int,
    val total: Int,
    val porcentaje: Int,
    val fecha: String
)
