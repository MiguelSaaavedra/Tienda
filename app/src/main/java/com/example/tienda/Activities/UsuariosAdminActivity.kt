package com.example.tienda.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tienda.R
import android.content.Intent
import android.widget.Button


class UsuariosAdminActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios_admin)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)
        //val ivcompras = findViewById<ImageView>(R.id.iv_compras_admin)
        val ivTodosUsuarios = findViewById<Button>(R.id.btn_ver_usuarios)
        val ivUnUsuario = findViewById<Button>(R.id.btn_ver_un_usuario)
        val ivCrearUsuario = findViewById<Button>(R.id.btn_crear_usuario)

        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }

        ivTodosUsuarios.setOnClickListener {
            val intent = Intent(this, VerUsuariosActivity::class.java)
            startActivity(intent)
        }

        ivUnUsuario.setOnClickListener {
            val intent = Intent(this, BuscarUnUsuario::class.java)
            startActivity(intent)
        }

        ivCrearUsuario.setOnClickListener {
            val intent = Intent(this, CrearUsuarioActivity::class.java)
            startActivity(intent)
        }

        ivproductos.setOnClickListener {
            val intent = Intent(this, ProductoAdminActivity::class.java)
            startActivity(intent)
        }
    }
}