package com.example.tienda.Models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreProducto: String,
    val precio: String,
    val imagen: String
) : Parcelable
