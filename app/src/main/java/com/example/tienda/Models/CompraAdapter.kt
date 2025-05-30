package com.example.tienda.Models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class CompraAdapter(
    private val compras: MutableList<CompraConDetalles>,
    private val onDespacharClick: (Int) -> Unit
    ) : RecyclerView.Adapter<CompraAdapter.CompraViewHolder>() {

    inner class CompraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProducto: TextView = itemView.findViewById(R.id.tv_Compra_nombrePro)
        val tvUsuario: TextView = itemView.findViewById(R.id.tv_compra_nombreUsu)
        val tvTelefono: TextView = itemView.findViewById(R.id.tv_compra_telefonoUsu)
        val tvPrecio: TextView = itemView.findViewById(R.id.tv_compra_costo)
        val btnDespachar: Button = itemView.findViewById(R.id.btn_despachado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = compras[position]

        holder.tvProducto.text = compra.nombreProducto
        holder.tvUsuario.text = compra.nombreUsuario
        holder.tvTelefono.text = compra.telefonoUsuario
        holder.tvPrecio.text = "$${compra.precioProducto}"

        holder.btnDespachar.setOnClickListener {
            onDespacharClick(compra.compraId)
        }
    }

    override fun getItemCount(): Int = compras.size

    fun removeCompraById(compraId: Int) {
        val index = compras.indexOfFirst { it.compraId == compraId }
        if (index != -1) {
            compras.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}