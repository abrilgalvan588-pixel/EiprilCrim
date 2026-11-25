package com.eiprilcrim.data.repository

import com.eiprilcrim.data.dao.UsuarioDao
import com.eiprilcrim.data.mapper.toDomain
import com.eiprilcrim.data.mapper.toEntity
import com.eiprilcrim.domain.model.Usuario
import com.eiprilcrim.domain.repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val usuarioDao: UsuarioDao
) : UsuarioRepository {

    override suspend fun insertarUsuario(usuario: Usuario) {
        usuarioDao.insertar(usuario.toEntity())
    }

    override suspend fun obtenerPorCorreo(correo: String): Usuario? {
        return usuarioDao.obtenerPorCorreo(correo)?.toDomain()
    }

    override suspend fun obtenerTodos(): List<Usuario> {
        return usuarioDao.obtenerTodos().map { it.toDomain() }
    }

    override suspend fun actualizarUsuario(usuario: Usuario) {
        usuarioDao.actualizar(usuario.toEntity())
    }

    override suspend fun eliminarUsuario(usuario: Usuario) {
        usuarioDao.eliminar(usuario.toEntity())
    }
}
