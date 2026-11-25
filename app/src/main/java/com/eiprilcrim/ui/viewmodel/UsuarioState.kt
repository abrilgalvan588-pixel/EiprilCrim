package com.eiprilcrim.ui.viewmodel

import com.eiprilcrim.domain.model.Usuario

data class UsuarioState(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val esRegistro: Boolean = false,
    val mensaje: String = "",
    val usuarioActual: Usuario? = null,
    val isLoading: Boolean = false
)
