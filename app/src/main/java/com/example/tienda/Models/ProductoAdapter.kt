package com.example.tienda.Models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class ProductoAdapter(
    private var listaProducto: List<Producto>,
    private val onEliminar: (Producto) -> Unit,
    private val onActualizar: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProducto[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = listaProducto.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        listaProducto = nuevaLista
        notifyDataSetChanged()
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreProducto: TextView = itemView.findViewById(R.id.tv_title_list)
        private val tvCosto: TextView = itemView.findViewById(R.id.tv_price_list)
        private val ivUbicacion: ImageView = itemView.findViewById(R.id.iv_book_list)
        private val btnActualizar: Button = itemView.findViewById(R.id.btn_actualizar_producto)
        private val btnEliminar: Button = itemView.findViewById(R.id.btn_eliminar_producto)

        fun bind(producto: Producto) {
            tvNombreProducto.text = "Nombre: ${producto.nombreProducto}"
            tvCosto.text = "Costo: ${producto.precio}"
            val context = itemView.context
            val imageResId = context.resources.getIdentifier(producto.imagen, "drawable", context.packageName)
            ivUbicacion.setImageResource(imageResId)


            btnActualizar.setOnClickListener {
                onActualizar(producto)
            }

            btnEliminar.setOnClickListener {
                onEliminar(producto)
            }
        }
    }
}