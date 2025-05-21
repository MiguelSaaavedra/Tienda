package com.example.tienda.Database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tienda.Models.Usuario
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tienda.Models.Compra
import com.example.tienda.Models.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Usuario::class, Producto::class, Compra::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao
    abstract fun compraDao(): CompraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tienda_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Registro predeterminado
                        CoroutineScope(Dispatchers.IO).launch {
                            getDatabase(context).usuarioDao().insertarUsuario(
                                Usuario(
                                    nombre = "Admin",
                                    apellido = "predeterminado",
                                    telefono = "0000",
                                    direccion = "calle",
                                    contrasena = "0000",
                                    tipoUsuario = "admin"
                                )
                            )
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

