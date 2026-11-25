package com.eiprilcrim.domain.repository

import com.eiprilcrim.domain.model.ResultadoQuiz

interface ResultadoQuizRepository {
    suspend fun guardarResultado(resultado: ResultadoQuiz)
    suspend fun obtenerUltimoResultado(idUsuario: Int): ResultadoQuiz?
    suspend fun obtenerHistorial(idUsuario: Int): List<ResultadoQuiz>
}
