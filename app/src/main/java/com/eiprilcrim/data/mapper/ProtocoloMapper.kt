package com.eiprilcrim.data.mapper

import com.eiprilcrim.data.entity.ProtocoloEntity
import com.eiprilcrim.domain.model.Protocolo

fun ProtocoloEntity.toDomain(): Protocolo {
    return Protocolo(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        pasos = if (pasosDelimitados.isBlank()) emptyList() else pasosDelimitados.split("||")
    )
}

fun Protocolo.toEntity(): ProtocoloEntity {
    return ProtocoloEntity(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        pasosDelimitados = pasos.joinToString("||")
    )
}
