package com.eiprilcrim.data.mapper

import com.eiprilcrim.data.entity.UsuarioEntity
import com.eiprilcrim.domain.model.Usuario

fun UsuarioEntity.toDomain(): Usuario {
    return Usuario(
        idUsuario = idUsuario,
        nombre = nombre,
        correo = correo,
        contrasena = contrasena
    )
}

fun Usuario.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        idUsuario = idUsuario,
        nombre = nombre,
        correo = correo,
        contrasena = contrasena
    )
}
