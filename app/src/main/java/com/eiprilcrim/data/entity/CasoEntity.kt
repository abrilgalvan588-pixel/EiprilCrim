package com.eiprilcrim.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "casos")
data class CasoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val nivel: String
)
