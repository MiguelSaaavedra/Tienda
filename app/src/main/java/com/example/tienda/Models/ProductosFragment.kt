package com.example.tienda.Models


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Activities.ActualizarProductoActivity
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var listaProductos: List<Producto>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ver_productos, container, false)
        recyclerView = view.findViewById(R.id.rv_productos)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        cargarProductos()
        return view
    }

    private fun cargarProductos() {
        val db = AppDatabase.getDatabase(requireContext())
        lifecycleScope.launch {
            listaProductos = withContext(Dispatchers.IO) {
                db.productoDao().obtenerTodosProductos()
            }

            productoAdapter = ProductoAdapter(
                listaProductos,
                onEliminar = { producto -> eliminarProducto(producto) },
                onActualizar = { producto ->
                    Toast.makeText(
                        requireContext(),
                        "Clic en actualizar: ${producto.nombreProducto}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(activity, ActualizarProductoActivity::class.java)
                    intent.putExtra("producto", producto)
                    actualizarProducto.launch(intent)
                }
            )
            recyclerView.adapter = productoAdapter
        }
    }

    private fun eliminarProducto(producto: Producto) {
        val db = AppDatabase.getDatabase(requireContext())
        lifecycleScope.launch(Dispatchers.IO) {
            db.productoDao().eliminarProducto(producto)
            withContext(Dispatchers.Main) {
                cargarProductos() // recargar lista
            }
        }
    }

    private val actualizarProducto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            cargarProductos()
        }
    }
}