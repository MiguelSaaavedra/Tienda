package com.example.tienda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tienda.Database.AppDatabase
import com.example.tienda.Models.Producto
import com.example.tienda.R
import kotlinx.coroutines.launch

class CrearProducto : AppCompatActivity() {

    private lateinit var etNombreProducto: EditText
    private lateinit var etCosto: EditText
    private lateinit var etUbicacion: EditText
    private lateinit var btnRegistrarProducto: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)

        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }



        etNombreProducto = findViewById<EditText>(R.id.et_name_producto)
        etCosto = findViewById<EditText>(R.id.et_precio_producto)
        etUbicacion = findViewById<EditText>(R.id.et_imagen_producto)
        btnRegistrarProducto = findViewById<Button>(R.id.btn_crear_producto_admin)

        btnRegistrarProducto.setOnClickListener {
            val nombre = etNombreProducto.text.toString().trim()
            val costo = etCosto.text.toString().trim()
            val ubicacion = etUbicacion.text.toString().trim()

            if (nombre.isNotBlank() && costo.isNotBlank() && ubicacion.isNotBlank()
            ) {

                val nuevoProducto = Producto(
                    nombreProducto = nombre,
                    precio = costo,
                    imagen = ubicacion
                )

                // Guardar usando Room en una corrutina
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@CrearProducto)
                    db.productoDao().insertarProducto(nuevoProducto)

                    runOnUiThread {
                        Toast.makeText(
                            this@CrearProducto,
                            "Producto registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                        limpiarCampos()
                        finish()
                    }
                }

            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun limpiarCampos() {
        etNombreProducto.text.clear()
        etCosto.text.clear()
        etUbicacion.text.clear()

    }
}