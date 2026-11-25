package com.eiprilcrim.domain.model

data class Usuario(
    val idUsuario: Int = 0,
    val nombre: String,
    val correo: String,
    val contrasena: String
)
