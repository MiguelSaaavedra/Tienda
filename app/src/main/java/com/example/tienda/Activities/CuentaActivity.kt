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
import com.example.tienda.Models.Usuario
import com.example.tienda.R
import kotlinx.coroutines.launch

class CuentaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnActualizar: Button
    private var usuarioId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuenta)

        etNombre = findViewById<EditText>(R.id.et_newname)
        etApellido = findViewById<EditText>(R.id.et_newfirst_name)
        etTelefono = findViewById<EditText>(R.id.et_newphone)
        etDireccion = findViewById<EditText>(R.id.et_newadress)
        etContrasena = findViewById<EditText>(R.id.et_newpassword)
        btnActualizar = findViewById<Button>(R.id.btn_actualizar)

        usuarioId = intent.getIntExtra("usuarioId", -1)

        if (usuarioId != -1) {
            val db = AppDatabase.getDatabase(this)
            lifecycleScope.launch {
                val usuario = db.usuarioDao().obtenerPorId(usuarioId)
                usuario?.let {
                    etNombre.setText(it.nombre)
                    etApellido.setText(it.apellido)
                    etTelefono.setText(it.telefono)
                    etDireccion.setText(it.direccion)
                    etContrasena.setText(it.contrasena)
                }
            }
        }
        btnActualizar.setOnClickListener {
            val db = AppDatabase.getDatabase(this)
            lifecycleScope.launch {
                val actualizado = Usuario(
                    id = usuarioId,
                    nombre = etNombre.text.toString(),
                    apellido = etApellido.text.toString(),
                    telefono = etTelefono.text.toString(),
                    direccion = etDireccion.text.toString(),
                    contrasena = etContrasena.text.toString()
                )
                db.usuarioDao().actualizarUsuario(actualizado)

                runOnUiThread {
                    Toast.makeText(this@CuentaActivity, "Datos actualizados", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

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
    }
}