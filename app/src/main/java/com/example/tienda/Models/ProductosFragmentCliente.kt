package com.example.tienda.Models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductosFragmentCliente : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapterCliente: ProductoAdapterCliente
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

            productoAdapterCliente = ProductoAdapterCliente(
                listaProductos,
                onAddToCart = { producto ->
                    CarritoManager.agregarProducto(producto)
                }
            )

            recyclerView.adapter = productoAdapterCliente
        }
    }
}