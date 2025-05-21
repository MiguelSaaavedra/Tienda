package com.example.tienda.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tienda.Models.Compra

@Dao
interface CompraDao {

    @Insert
    suspend fun insertarCompra(compra: Compra)

    @Query("SELECT * FROM compra")
    suspend fun obtenerComprasPorUsuario(): List<Compra>

    @Delete
    suspend fun eliminarCompra(compra: Compra)
}