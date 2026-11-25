package com.eiprilcrim.data.repository

import com.eiprilcrim.data.dao.GlosarioDao
import com.eiprilcrim.data.mapper.toDomain
import com.eiprilcrim.data.mapper.toEntity
import com.eiprilcrim.domain.model.TerminoGlosario
import com.eiprilcrim.domain.repository.GlosarioRepository

class GlosarioRepositoryImpl(
    private val glosarioDao: GlosarioDao
) : GlosarioRepository {

    override suspend fun insertarTermino(termino: TerminoGlosario) {
        glosarioDao.insertar(termino.toEntity())
    }

    override suspend fun insertarTerminos(terminos: List<TerminoGlosario>) {
        glosarioDao.insertarTodos(terminos.map { it.toEntity() })
    }

    override suspend fun obtenerTodos(): List<TerminoGlosario> {
        return glosarioDao.obtenerTodos().map { it.toDomain() }
    }

    override suspend fun buscarTerminos(busqueda: String): List<TerminoGlosario> {
        return glosarioDao.buscarTerminos(busqueda.trim()).map { it.toDomain() }
    }
}
