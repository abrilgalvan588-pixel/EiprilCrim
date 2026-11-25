package com.eiprilcrim.domain.repository

import com.eiprilcrim.domain.model.Protocolo

interface ProtocoloRepository {
    suspend fun insertarProtocolo(protocolo: Protocolo)
    suspend fun insertarProtocolos(protocolos: List<Protocolo>)
    suspend fun obtenerTodos(): List<Protocolo>
    suspend fun buscarProtocolos(busqueda: String): List<Protocolo>
}
