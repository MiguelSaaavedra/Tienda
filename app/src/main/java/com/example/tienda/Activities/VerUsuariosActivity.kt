package com.example.tienda.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tienda.R
import android.content.Intent
import com.example.tienda.Models.UsuariosFragment


class VerUsuariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuarios)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)
        //val ivcompras = findViewById<ImageView>(R.id.iv_compras_admin)

        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }

        ivproductos.setOnClickListener {
            val intent = Intent(this, ProductoAdminActivity::class.java)
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UsuariosFragment())
            .commit()

    }
}