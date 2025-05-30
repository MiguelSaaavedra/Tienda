package com.example.tienda.Models


data class CompraConDetalles(
    val compraId: Int,
    val nombreUsuario: String,
    val telefonoUsuario: String,
    val nombreProducto: String,
    val precioProducto: Int
)