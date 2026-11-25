package com.eiprilcrim.data.mapper

import com.eiprilcrim.data.entity.GlosarioEntity
import com.eiprilcrim.domain.model.TerminoGlosario

fun GlosarioEntity.toDomain(): TerminoGlosario {
    return TerminoGlosario(
        id = id,
        termino = termino,
        definicion = definicion
    )
}

fun TerminoGlosario.toEntity(): GlosarioEntity {
    return GlosarioEntity(
        id = id,
        termino = termino,
        definicion = definicion
    )
}
