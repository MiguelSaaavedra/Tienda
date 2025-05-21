package com.example.tienda.Database

import androidx.room.*
import com.example.tienda.Models.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    suspend fun obtenerTodos(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Usuario?

    @Update
    suspend fun actualizarUsuario(usuario: Usuario)

    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE telefono = :telefono AND contrasena = :contrasena LIMIT 1")
    suspend fun login(telefono: String, contrasena: String): Usuario?
}