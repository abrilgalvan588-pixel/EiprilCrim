package com.eiprilcrim.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "glosario")
data class GlosarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val termino: String,
    val definicion: String
)
