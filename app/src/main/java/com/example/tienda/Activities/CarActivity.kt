package com.example.tienda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Models.CarritoAdapter
import com.example.tienda.Models.CarritoFragment
import com.example.tienda.Models.CarritoManager
import com.example.tienda.R

class CarActivity : AppCompatActivity(){

    private var usuarioId: Int = -1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        usuarioId = intent.getIntExtra("usuarioId", -1)
        val ivhome = findViewById<ImageView>(R.id.iv_home)
        val ivcar = findViewById<ImageView>(R.id.iv_car)
        val ivmap = findViewById<ImageView>(R.id.iv_map)
        val ivuser = findViewById<ImageView>(R.id.iv_user)

        ivhome.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        ivcar.setOnClickListener {
            val intent = Intent(this, CarActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        ivmap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        ivuser.setOnClickListener {
            val intent = Intent(this, CuentaActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_carrito, CarritoFragment())
            .commit()

    }

}