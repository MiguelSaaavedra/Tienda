package com.example.tienda.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tienda.Models.CompraFragment
import com.example.tienda.R

class ComprasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_compras)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)
        val ivcompras = findViewById<ImageView>(R.id.iv_comprasAdmin)

        ivusuarios.setOnClickListener {
            startActivity(Intent(this, UsuariosAdminActivity::class.java))
        }

        ivproductos.setOnClickListener {
            startActivity(Intent(this, ProductoAdminActivity::class.java))
        }

        ivcompras.setOnClickListener {
            startActivity(Intent(this, ComprasActivity::class.java))
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_compras, CompraFragment())
            .commit()
    }


}