package com.example.tienda.Models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class CarritoAdapter (private val items: List<Producto>): RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen = itemView.findViewById<ImageView>(R.id.iv_book_carrito)
        val nombre = itemView.findViewById<TextView>(R.id.tv_title_carrito)
        val precio = itemView.findViewById<TextView>(R.id.tv_price_carrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = items[position]
        holder.nombre.text = item.nombreProducto
        holder.precio.text = "$${item.precio}"


        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(item.imagen, "drawable", context.packageName)
        holder.imagen.setImageResource(resId)
    }
    override fun getItemCount(): Int = items.size
}