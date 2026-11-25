package com.eiprilcrim.data.repository

import com.eiprilcrim.data.dao.ProtocoloDao
import com.eiprilcrim.data.mapper.toDomain
import com.eiprilcrim.data.mapper.toEntity
import com.eiprilcrim.domain.model.Protocolo
import com.eiprilcrim.domain.repository.ProtocoloRepository

class ProtocoloRepositoryImpl(
    private val protocoloDao: ProtocoloDao
) : ProtocoloRepository {

    override suspend fun insertarProtocolo(protocolo: Protocolo) {
        protocoloDao.insertar(protocolo.toEntity())
    }

    override suspend fun insertarProtocolos(protocolos: List<Protocolo>) {
        protocoloDao.insertarTodos(protocolos.map { it.toEntity() })
    }

    override suspend fun obtenerTodos(): List<Protocolo> {
        return protocoloDao.obtenerTodos().map { it.toDomain() }
    }

    override suspend fun buscarProtocolos(busqueda: String): List<Protocolo> {
        return protocoloDao.buscarProtocolos(busqueda.trim()).map { it.toDomain() }
    }
}
