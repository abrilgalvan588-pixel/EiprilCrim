package com.eiprilcrim.data.mapper

import com.eiprilcrim.data.entity.ResultadoQuizEntity
import com.eiprilcrim.domain.model.ResultadoQuiz

fun ResultadoQuizEntity.toDomain(): ResultadoQuiz {
    return ResultadoQuiz(
        id = id,
        idUsuario = idUsuario,
        correctas = correctas,
        total = total,
        porcentaje = porcentaje,
        fecha = fecha
    )
}

fun ResultadoQuiz.toEntity(): ResultadoQuizEntity {
    return ResultadoQuizEntity(
        id = id,
        idUsuario = idUsuario,
        correctas = correctas,
        total = total,
        porcentaje = porcentaje,
        fecha = fecha
    )
}
