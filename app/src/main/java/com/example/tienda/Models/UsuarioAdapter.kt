package com.example.tienda.Models


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R

class UsuarioAdapter(
    private var listaUsuarios: List<Usuario>,
    private val onEliminar: (Usuario) -> Unit,
    private val onActualizar: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        holder.bind(usuario)
    }

    override fun getItemCount(): Int = listaUsuarios.size

    fun actualizarLista(nuevaLista: List<Usuario>) {
        listaUsuarios = nuevaLista
        notifyDataSetChanged()
    }

    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tv_lista_nombre)
        private val tvTelefono: TextView = itemView.findViewById(R.id.tv_lista_telefono)
        private val tvDireccion: TextView = itemView.findViewById(R.id.tv_lista_direccion)
        private val tvContrasena: TextView = itemView.findViewById(R.id.tv_lista_contrasena)
        private val btnActualizar: Button = itemView.findViewById(R.id.btn_actualizar_usuario)
        private val btnEliminar: Button = itemView.findViewById(R.id.btn_eliminar)

        fun bind(usuario: Usuario) {
            tvNombre.text = "Nombre: ${usuario.nombre}" + " " + "${usuario.apellido}"
            tvTelefono.text = "Teléfono: ${usuario.telefono}"
            tvDireccion.text = "Dirección: ${usuario.direccion}"
            tvContrasena.text = "Contraseña: ${usuario.contrasena}"

            btnActualizar.setOnClickListener {
                onActualizar(usuario)
            }

            btnEliminar.setOnClickListener {
                onEliminar(usuario)
            }
        }
    }
}