package com.eiprilcrim.domain.repository

import com.eiprilcrim.domain.model.Usuario

interface UsuarioRepository {
    suspend fun insertarUsuario(usuario: Usuario)
    suspend fun obtenerPorCorreo(correo: String): Usuario?
    suspend fun obtenerTodos(): List<Usuario>
    suspend fun actualizarUsuario(usuario: Usuario)
    suspend fun eliminarUsuario(usuario: Usuario)
}
