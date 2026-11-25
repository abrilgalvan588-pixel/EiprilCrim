package com.eiprilcrim.domain.repository

import com.eiprilcrim.domain.model.Caso

interface CasoRepository {
    suspend fun insertarCaso(caso: Caso)
    suspend fun insertarCasos(casos: List<Caso>)
    suspend fun obtenerTodos(): List<Caso>
    suspend fun obtenerPorId(id: Int): Caso?
    suspend fun buscarCasos(busqueda: String, nivel: String): List<Caso>
}
