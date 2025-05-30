package com.example.tienda.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tienda.Models.Compra
import com.example.tienda.Models.CompraConDetalles

@Dao
interface CompraDao {

    @Insert
    suspend fun insertarCompra(compra: Compra)

    @Query("SELECT * FROM compra")
    suspend fun obtenerComprasPorUsuario(): List<Compra>

    @Delete
    suspend fun eliminarCompra(compra: Compra)

    @Query("DELETE FROM compra WHERE id = :compraId")
    suspend fun eliminarCompraPorId(compraId: Int)

    @Query("""
    SELECT 
        compra.id AS compraId,
        usuario.nombre AS nombreUsuario,
        usuario.telefono AS telefonoUsuario,
        producto.nombreProducto AS nombreProducto,
        producto.precio AS precioProducto
    FROM compra
    INNER JOIN usuario ON compra.usuario_id = usuario.id
    INNER JOIN producto ON compra.producto_id = producto.id
""")
    suspend fun obtenerComprasConDetalles(): List<CompraConDetalles>
}