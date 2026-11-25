package com.eiprilcrim.domain.model

data class Protocolo(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val pasos: List<String> = emptyList()
)
