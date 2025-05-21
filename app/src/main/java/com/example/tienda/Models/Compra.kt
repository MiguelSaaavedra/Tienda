package com.example.tienda.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "compra")
data class Compra(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Int,

    @ColumnInfo(name = "producto_id")
    val productoId: Int,

    @ColumnInfo(name = "fecha")
    val fecha: Long = System.currentTimeMillis()  // guardamos la fecha en milisegundos
)