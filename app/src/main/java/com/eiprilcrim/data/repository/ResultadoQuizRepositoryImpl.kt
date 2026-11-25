package com.eiprilcrim.data.repository

import com.eiprilcrim.data.dao.ResultadoQuizDao
import com.eiprilcrim.data.mapper.toDomain
import com.eiprilcrim.data.mapper.toEntity
import com.eiprilcrim.domain.model.ResultadoQuiz
import com.eiprilcrim.domain.repository.ResultadoQuizRepository

class ResultadoQuizRepositoryImpl(
    private val resultadoQuizDao: ResultadoQuizDao
) : ResultadoQuizRepository {

    override suspend fun guardarResultado(resultado: ResultadoQuiz) {
        resultadoQuizDao.insertar(resultado.toEntity())
    }

    override suspend fun obtenerUltimoResultado(idUsuario: Int): ResultadoQuiz? {
        return resultadoQuizDao.obtenerUltimoPorUsuario(idUsuario)?.toDomain()
    }

    override suspend fun obtenerHistorial(idUsuario: Int): List<ResultadoQuiz> {
        return resultadoQuizDao.obtenerHistorial(idUsuario).map { it.toDomain() }
    }
}
