package com.example.tienda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuscarUnProducto : AppCompatActivity() {

    private lateinit var etBuscarId: EditText
    private lateinit var btnBuscar: Button
    private lateinit var tvResultado: TextView
    private lateinit var ivusuarios: ImageView
    private lateinit var ivcompras: ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_un_producto)

        etBuscarId = findViewById(R.id.et_buscar_id_producto)
        btnBuscar = findViewById(R.id.btn_buscar_producto)
        tvResultado = findViewById(R.id.tv_resultado_producto)
        ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        ivcompras = findViewById<ImageView>(R.id.iv_comprasAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)

        ivproductos.setOnClickListener {
            val intent = Intent(this, ProductoAdminActivity::class.java)
            startActivity(intent)
        }

        ivcompras.setOnClickListener {
            val intent = Intent(this, ComprasActivity::class.java)
            startActivity(intent)
        }

        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }

        btnBuscar.setOnClickListener {
            val idTexto = etBuscarId.text.toString().trim()
            if (idTexto.isNotEmpty()) {
                val id = idTexto.toIntOrNull()
                if (id != null) {
                    obtenerProductoPorId(id)
                } else {
                    Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerProductoPorId(id: Int) {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@BuscarUnProducto)
            val producto = withContext(Dispatchers.IO) {
                db.productoDao().obtenerProductoPorId(id)
            }

            if (producto != null) {
                val texto = """
                    ID: ${producto.id}
                    Titulo: ${producto.nombreProducto}
                    Precio: ${producto.precio}
                    Ubicación de la imagen: ${producto.imagen}
                """.trimIndent()
                tvResultado.text = texto
            } else {
                tvResultado.text = "No se encontró un producto con ese ID."
            }
        }
    }

}

