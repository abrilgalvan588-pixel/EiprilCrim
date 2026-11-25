package com.eiprilcrim.data.mapper

import com.eiprilcrim.data.entity.CasoEntity
import com.eiprilcrim.domain.model.Caso

fun CasoEntity.toDomain(): Caso {
    return Caso(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        categoria = categoria,
        nivel = nivel
    )
}

fun Caso.toEntity(): CasoEntity {
    return CasoEntity(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        categoria = categoria,
        nivel = nivel
    )
}
