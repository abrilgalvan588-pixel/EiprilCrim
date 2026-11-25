package com.eiprilcrim.data.repository

import com.eiprilcrim.data.dao.CasoDao
import com.eiprilcrim.data.mapper.toDomain
import com.eiprilcrim.data.mapper.toEntity
import com.eiprilcrim.domain.model.Caso
import com.eiprilcrim.domain.repository.CasoRepository

class CasoRepositoryImpl(
    private val casoDao: CasoDao
) : CasoRepository {

    override suspend fun insertarCaso(caso: Caso) {
        casoDao.insertar(caso.toEntity())
    }

    override suspend fun insertarCasos(casos: List<Caso>) {
        casoDao.insertarTodos(casos.map { it.toEntity() })
    }

    override suspend fun obtenerTodos(): List<Caso> {
        return casoDao.obtenerTodos().map { it.toDomain() }
    }

    override suspend fun obtenerPorId(id: Int): Caso? {
        return casoDao.obtenerPorId(id)?.toDomain()
    }

    override suspend fun buscarCasos(busqueda: String, nivel: String): List<Caso> {
        return casoDao.buscarCasos(busqueda.trim(), nivel).map { it.toDomain() }
    }
}
