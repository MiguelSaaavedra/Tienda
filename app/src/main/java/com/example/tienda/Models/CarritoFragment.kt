package com.example.tienda.Models


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarritoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnComprar: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ver_carrito, container, false)

        recyclerView = view.findViewById(R.id.rv_carrito)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CarritoAdapter(CarritoManager.obtenerProductos())

        btnComprar = view.findViewById(R.id.btn_comprar)
        btnComprar.setOnClickListener {
            finalizarCompra()
        }

        return view
    }

    private fun finalizarCompra() {
        val productos = CarritoManager.obtenerProductos()
        val usuarioId = requireActivity().intent.getIntExtra("usuarioId", -1)

        if (usuarioId == -1 || productos.isEmpty()) {
            Toast.makeText(requireContext(), "Error: usuario no válido o carrito vacío", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val compraDao = AppDatabase.getDatabase(requireContext()).compraDao()

            productos.forEach { producto ->
                val compra = Compra(
                    usuarioId = usuarioId,
                    productoId = producto.id
                )
                compraDao.insertarCompra(compra)
            }

            CarritoManager.limpiar()

            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Gracias por tu compra", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = CarritoAdapter(emptyList())
            }
        }
    }
}