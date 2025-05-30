package com.example.tienda.Models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Database.AppDatabase
import com.example.tienda.R
import kotlinx.coroutines.launch

class CompraFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CompraAdapter
    private lateinit var db: AppDatabase
    private var compras = mutableListOf<CompraConDetalles>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ver_compra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_compras)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        db = AppDatabase.getDatabase(requireContext())

        adapter = CompraAdapter(compras) { compraId ->
            eliminarCompra(compraId)
        }

        recyclerView.adapter = adapter

        obtenerCompras()
    }

    private fun obtenerCompras() {
        lifecycleScope.launch {
            val lista = db.compraDao().obtenerComprasConDetalles()
            compras.clear()
            compras.addAll(lista)
            adapter.notifyDataSetChanged()
        }
    }

    private fun eliminarCompra(compraId: Int) {
        lifecycleScope.launch {
            db.compraDao().eliminarCompraPorId(compraId)
            adapter.removeCompraById(compraId)
            Toast.makeText(requireContext(), "Compra despachada", Toast.LENGTH_SHORT).show()
        }
    }
}