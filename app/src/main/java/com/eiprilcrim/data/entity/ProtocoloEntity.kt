package com.eiprilcrim.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "protocolos")
data class ProtocoloEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val pasosDelimitados: String
)
