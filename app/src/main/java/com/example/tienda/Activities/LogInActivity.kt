package com.example.tienda.Activities

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import com.google.android.gms.tasks.Task

class LogInActivity : AppCompatActivity() {

    private lateinit var etTelefono: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegistrarse: TextView
    private lateinit var btnGoogle: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        etTelefono = findViewById(R.id.et_user)
        etContrasena = findViewById(R.id.et_password)

        btnLogin = findViewById(R.id.btn_logIn)
        tvRegistrarse = findViewById(R.id.tv_registrarse)
        btnGoogle = findViewById(R.id.btn_logIn_google)

        btnGoogle.setOnClickListener {
            signIn()
        }


        btnLogin.setOnClickListener {
            val telefono = etTelefono.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (telefono.isNotBlank() && contrasena.isNotBlank()) {
                lifecycleScope.launch {
                    try {
                        val db = AppDatabase.getDatabase(this@LogInActivity)
                        val usuario = db.usuarioDao().login(telefono, contrasena)

                        runOnUiThread {
                            if (usuario != null) {
                                Toast.makeText(
                                    this@LogInActivity,
                                    "Bienvenido ${usuario.nombre}",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Ir a ProductActivity
                                val intent = when (usuario.tipoUsuario.lowercase()) {
                                    "cliente" -> Intent(this@LogInActivity, ProductActivity::class.java)
                                    else -> Intent(this@LogInActivity, UsuariosAdminActivity::class.java)
                                }


                                intent.putExtra(
                                    "usuarioId",
                                    usuario.id
                                ) // opcional: pasar info del usuario
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(
                                    this@LogInActivity,
                                    "Datos incorrectos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(
                                this@LogInActivity,
                                "Error en el login: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        tvRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn (){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d(TAG, "signInSuccess: ${account.email}")
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

            intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("email", account.email)
            intent.putExtra("name", account.displayName)
            startActivity(intent)

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
        }
    }

}