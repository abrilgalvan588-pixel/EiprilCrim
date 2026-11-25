package com.eiprilcrim.domain.model

data class Caso(
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val nivel: String
)
