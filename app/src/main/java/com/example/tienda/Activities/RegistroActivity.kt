package com.example.tienda.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tienda.Database.AppDatabase
import com.example.tienda.Models.Usuario
import com.example.tienda.R
import kotlinx.coroutines.launch


class RegistroActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etNombre = findViewById<EditText>(R.id.et_name)
        etApellido = findViewById<EditText>(R.id.et_first_name)
        etTelefono = findViewById<EditText>(R.id.et_phone)
        etDireccion = findViewById<EditText>(R.id.et_adress)
        etContrasena = findViewById<EditText>(R.id.et_password)
        btnRegistrar = findViewById<Button>(R.id.btn_registrar)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (nombre.isNotBlank() && apellido.isNotBlank() && telefono.isNotBlank()
                && direccion.isNotBlank() && contrasena.isNotBlank()
            ) {

                val nuevoUsuario = Usuario(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    direccion = direccion,
                    contrasena = contrasena,
                    tipoUsuario = "cliente" // opcional, ya se define por defecto
                )

                // Guardar usando Room en una corrutina
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@RegistroActivity)
                    db.usuarioDao().insertarUsuario(nuevoUsuario)

                    runOnUiThread {
                        Toast.makeText(
                            this@RegistroActivity,
                            "Usuario registrado",
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
        etNombre.text.clear()
        etApellido.text.clear()
        etTelefono.text.clear()
        etDireccion.text.clear()
        etContrasena.text.clear()
    }
}
