package com.example.tienda.Activities

import android.content.Intent
import android.net.Uri
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
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CuentaActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnActualizar: Button
    private lateinit var photo: ImageView
    private lateinit var enlaceCamara: Button
    private var currentPhotoPath: String? = null
    private lateinit var photoURI: Uri
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSIONS = 100
    }
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

        photo = findViewById(R.id.iv_Photo)
        enlaceCamara = findViewById(R.id.btn_tomarfoto)

        enlaceCamara.setOnClickListener {

            if (checkCameraPermission()) {
                dispatchTakePictureIntent()
            } else {
                requestCameraPermissions()
            }
        }

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

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSIONS
        )
    }

    private fun createImageFile(): File? {
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timestamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile : File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(
                        this,
                        "Error al crear la imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                    null
                }

                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "${packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photo.setImageURI(photoURI)
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )==PackageManager.PERMISSION_GRANTED
    }
}