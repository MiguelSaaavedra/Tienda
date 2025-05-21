package com.example.tienda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tienda.Models.ProductosFragment
import com.example.tienda.Models.UsuariosFragment
import com.example.tienda.R

class VerProductosActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_productos)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)

        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_productos, ProductosFragment())
            .commit()

    }
}