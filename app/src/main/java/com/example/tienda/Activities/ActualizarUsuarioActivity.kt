package com.example.tienda.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tienda.Database.AppDatabase
import com.example.tienda.Models.Usuario
import com.example.tienda.R
import kotlinx.coroutines.launch

class ActualizarUsuarioActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var opcionSeleccionada: String
    private var usuario: Usuario? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_usuario)

        val ivusuarios = findViewById<ImageView>(R.id.iv_usuariosAdmin)
        val ivproductos = findViewById<ImageView>(R.id.iv_productos)
        val spinnerOpciones = findViewById<Spinner>(R.id.tipo_usuario_admin_update)




        etNombre = findViewById(R.id.et_name_update)
        etApellido = findViewById(R.id.et_first_name_update)
        etTelefono = findViewById(R.id.et_phone_update)
        etDireccion = findViewById(R.id.et_adress_update)
        etContrasena = findViewById(R.id.et_password_update)
        btnRegistrar = findViewById(R.id.btn_usuario_admin_update)

        usuario = intent.getParcelableExtra("usuario")


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
                    this@ActualizarUsuarioActivity,
                    "Seleccionaste: $opcionSeleccionada",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Puedes dejarlo vacío o manejar este caso
            }
        }

        usuario?.let {
            etNombre.setText(it.nombre)
            etApellido.setText(it.apellido)
            etTelefono.setText(it.telefono)
            etDireccion.setText(it.direccion)
            etContrasena.setText(it.contrasena)

            val index = resources.getStringArray(R.array.opciones_spinner)
                .indexOf(it.tipoUsuario)
            if (index >= 0) spinnerOpciones.setSelection(index)
        }

        ivusuarios.setOnClickListener {
            startActivity(Intent(this, UsuariosAdminActivity::class.java))
        }

        ivproductos.setOnClickListener {
            startActivity(Intent(this, ProductoAdminActivity::class.java))
        }


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

                val usuarioActualizado = Usuario(
                    id = usuario?.id ?: 0,
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    direccion = direccion,
                    contrasena = contrasena,
                    tipoUsuario = tipoUsuario
                )

                // Guardar usando Room en una corrutina
                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@ActualizarUsuarioActivity)
                    db.usuarioDao().actualizarUsuario(usuarioActualizado)

                    runOnUiThread {
                        Toast.makeText(
                            this@ActualizarUsuarioActivity,
                            "Usuario actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                        setResult(RESULT_OK)
                        finish()
                    }
                }

            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}