package com.example.tienda.Models

import com.example.tienda.Models.Producto

object CarritoManager {
    private val productosEnCarrito = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        productosEnCarrito.add(producto)
    }

    fun obtenerProductos(): List<Producto> = productosEnCarrito

    fun limpiar() {
        productosEnCarrito.clear()
    }
}