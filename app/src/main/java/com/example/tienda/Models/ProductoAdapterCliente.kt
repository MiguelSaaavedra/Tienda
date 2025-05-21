package com.example.tienda.Models

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class ProductoAdapterCliente (
    private val productos: List<Producto>,
    private val onAddToCart: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapterCliente.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre = itemView.findViewById<TextView>(R.id.tv_title_list)
        val precio = itemView.findViewById<TextView>(R.id.tv_price_list)
        val imagen = itemView.findViewById<ImageView>(R.id.iv_book_list)
        val btnAgregarCarrito = itemView.findViewById<Button>(R.id.btn_añadir_carrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_cliente, parent, false)
        return ProductoViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombreProducto
        holder.precio.text = producto.precio.toString()

        holder.btnAgregarCarrito.setOnClickListener {
            onAddToCart(producto)
            Toast.makeText(holder.itemView.context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()
        }

        val context = holder.itemView.context
        val imageResId = context.resources.getIdentifier(producto.imagen, "drawable", context.packageName)
        if (imageResId != 0) {
            holder.imagen.setImageResource(imageResId)
        } else {

            holder.imagen.setImageResource(R.drawable.product_green)
        }
    }

    override fun getItemCount(): Int = productos.size
}