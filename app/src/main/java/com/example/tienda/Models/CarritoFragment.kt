package com.example.tienda.Models


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class CarritoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnComprar: Button

    @SuppressLint("MissingInflatedId")
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
            Toast.makeText(requireContext(), "Gracias por tu compra", Toast.LENGTH_SHORT).show()
            CarritoManager.limpiar()
            recyclerView.adapter?.notifyDataSetChanged()
        }

        return view
    }
}