package com.example.tienda.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tienda.Models.Producto

@Dao
interface ProductoDao {

    @Query("SELECT * FROM producto")
    suspend fun obtenerTodosProductos(): List<Producto>

    @Query("SELECT * FROM producto WHERE id = :id")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    @Insert
    suspend fun insertarProducto(producto: Producto)

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)
}