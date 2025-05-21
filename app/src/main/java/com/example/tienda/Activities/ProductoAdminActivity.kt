package com.example.tienda.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.widget.ImageView
import com.example.tienda.R
import android.content.Intent
import android.widget.Button

class ProductoAdminActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_admin)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)
        val ivCrearProducto = findViewById<Button>(R.id.btn_crear_producto)
        val ivlistaProductos = findViewById<Button>(R.id.btn_ver_producto)
        val btnUnProducto = findViewById<Button>(R.id.btn_ver_un_producto)

        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }

        ivCrearProducto.setOnClickListener {
            val intent = Intent(this, CrearProducto::class.java)
            startActivity(intent)
        }

        ivproductos.setOnClickListener {
            val intent = Intent(this, ProductoAdminActivity::class.java)
            startActivity(intent)
        }

        ivlistaProductos.setOnClickListener {
            val intent = Intent(this, VerProductosActivity::class.java)
            startActivity(intent)
        }

        btnUnProducto.setOnClickListener {
            val intent = Intent(this, BuscarUnProducto::class.java)
            startActivity(intent)
        }


    }

}