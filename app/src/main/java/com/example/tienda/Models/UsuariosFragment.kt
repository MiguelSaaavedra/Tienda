package com.example.tienda.Models

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Activities.ActualizarUsuarioActivity
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var usuarioAdapter: UsuarioAdapter
    private lateinit var listaUsuarios: List<Usuario>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ver_usuarios, container, false)
        recyclerView = view.findViewById(R.id.rv_usuarios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cargarUsuarios()
        return view
    }

    private fun cargarUsuarios() {
        val db = AppDatabase.getDatabase(requireContext())
        lifecycleScope.launch {
            listaUsuarios = withContext(Dispatchers.IO) {
                db.usuarioDao().obtenerTodos()
            }

            usuarioAdapter = UsuarioAdapter(listaUsuarios,
                onEliminar = { usuario -> eliminarUsuario(usuario) },
                onActualizar = { usuario ->
                    Toast.makeText(requireContext(), "Clic en actualizar: ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, ActualizarUsuarioActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    actualizarUsuarioLauncher.launch(intent) }
            )
            recyclerView.adapter = usuarioAdapter
        }
    }

    private fun eliminarUsuario(usuario: Usuario) {
        val db = AppDatabase.getDatabase(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            db.usuarioDao().eliminarUsuario(usuario)
            withContext(Dispatchers.Main) {
                cargarUsuarios() // recargar lista
            }
        }
    }

    private val actualizarUsuarioLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            cargarUsuarios() // recargar la lista si se actualizó un usuario
        }
    }


}