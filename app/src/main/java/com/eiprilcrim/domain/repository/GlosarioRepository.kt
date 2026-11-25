package com.eiprilcrim.domain.repository

import com.eiprilcrim.domain.model.TerminoGlosario

interface GlosarioRepository {
    suspend fun insertarTermino(termino: TerminoGlosario)
    suspend fun insertarTerminos(terminos: List<TerminoGlosario>)
    suspend fun obtenerTodos(): List<TerminoGlosario>
    suspend fun buscarTerminos(busqueda: String): List<TerminoGlosario>
}
