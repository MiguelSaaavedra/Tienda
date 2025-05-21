package com.example.tienda.Activities

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

class ActualizarProductoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCosto: EditText
    private lateinit var etUbicacion: EditText
    private lateinit var btnActualizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_producto)

        var producto = intent.getParcelableExtra<Producto>("producto")
        Toast.makeText(this, "Producto recibido: ${producto?.nombreProducto}", Toast.LENGTH_SHORT).show()

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)

        etNombre = findViewById(R.id.et_titulo_update)
        etCosto = findViewById(R.id.et_costo_update)
        etUbicacion = findViewById(R.id.et_ubicacion_update)
        btnActualizar = findViewById(R.id.btn_producto_admin_update)

        producto?.let {
            etNombre.setText(it.nombreProducto)
            etCosto.setText(it.precio)
            etUbicacion.setText(it.imagen)
        }

        ivusuarios.setOnClickListener {
            startActivity(Intent(this, UsuariosAdminActivity::class.java))
        }

        ivproductos.setOnClickListener {
            startActivity(Intent(this, ProductoAdminActivity::class.java))
        }

        btnActualizar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val costo = etCosto.text.toString().trim()
            val ubicacion = etUbicacion.text.toString().trim()

            if (nombre.isNotBlank() && costo.isNotBlank() && ubicacion.isNotBlank()
            ) {

                val productoActualizado = Producto(
                    id = producto!!.id,
                    nombreProducto = nombre,
                    precio = costo,
                    imagen = ubicacion
                )

                // Guardar usando Room en una corrutina
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@ActualizarProductoActivity)
                    db.productoDao().actualizarProducto(productoActualizado)

                    runOnUiThread {
                        Toast.makeText(
                            this@ActualizarProductoActivity,
                            "Producto actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                        setResult(RESULT_OK)
                        finish()
                    }
                }

            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}