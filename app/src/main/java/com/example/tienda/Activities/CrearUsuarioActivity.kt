package com.example.tienda.Activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tienda.R
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.tienda.Database.AppDatabase
import com.example.tienda.Models.Usuario
import kotlinx.coroutines.launch


class CrearUsuarioActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var opcionSeleccionada: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_usuario)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val spinnerOpciones = findViewById<Spinner>(R.id.tipo_usuario_admin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)


        ivusuarios.setOnClickListener {
            val intent = Intent(this, UsuariosAdminActivity::class.java)
            startActivity(intent)
        }

        ivproductos.setOnClickListener {
            val intent = Intent(this, ProductoAdminActivity::class.java)
            startActivity(intent)
        }

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_spinner, // ID del array
            android.R.layout.simple_spinner_item // Layout por defecto
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinnerOpciones.adapter = adapter

        spinnerOpciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                opcionSeleccionada = parent?.getItemAtPosition(position).toString()
                Toast.makeText(
                    this@CrearUsuarioActivity,
                    "Seleccionaste: $opcionSeleccionada",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Puedes dejarlo vacío o manejar este caso
            }
        }

        etNombre = findViewById<EditText>(R.id.et_name)
        etApellido = findViewById<EditText>(R.id.et_first_name)
        etTelefono = findViewById<EditText>(R.id.et_phone)
        etDireccion = findViewById<EditText>(R.id.et_adress)
        etContrasena = findViewById<EditText>(R.id.et_password)
        btnRegistrar = findViewById<Button>(R.id.btn_crear_usuario_admin)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()
            val tipoUsuario = opcionSeleccionada.toString().trim()

            if (nombre.isNotBlank() && apellido.isNotBlank() && telefono.isNotBlank()
                && direccion.isNotBlank() && contrasena.isNotBlank()
            ) {

                val nuevoUsuario = Usuario(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    direccion = direccion,
                    contrasena = contrasena,
                    tipoUsuario = tipoUsuario
                )

                // Guardar usando Room en una corrutina
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@CrearUsuarioActivity)
                    db.usuarioDao().insertarUsuario(nuevoUsuario)

                    runOnUiThread {
                        Toast.makeText(
                            this@CrearUsuarioActivity,
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
