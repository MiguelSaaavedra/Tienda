package com.example.tienda.Activities


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.Database.AppDatabase
import com.example.tienda.Models.Producto
import com.example.tienda.Models.ProductoAdapterCliente
import com.example.tienda.Models.ProductosFragment
import com.example.tienda.Models.ProductosFragmentCliente
import com.example.tienda.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductActivity : AppCompatActivity() {

    private var usuarioId: Int = -1
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: ProductoAdapterCliente
    private lateinit var listaProductos: List<Producto>
    private val carrito = mutableListOf<Producto>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        usuarioId = intent.getIntExtra("usuarioId", -1)
        recyclerView = findViewById(R.id.rv_productos)
        recyclerView?.layoutManager = GridLayoutManager(this, 2)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            listaProductos = withContext(Dispatchers.IO) {
                db.productoDao().obtenerTodosProductos()
            }

            adapter = ProductoAdapterCliente(listaProductos) { producto ->
                Toast.makeText(this@ProductActivity, "Añadido: ${producto.nombreProducto}", Toast.LENGTH_SHORT).show()
            }

            recyclerView?.adapter = adapter
        }

        setupNavegacion()
    }

    fun agregarAlCarrito(producto: Producto) {
        carrito.add(producto)
        Toast.makeText(this, "${producto.nombreProducto} añadido al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun setupNavegacion() {
        val ivhome = findViewById<ImageView>(R.id.iv_home)
        val ivcar = findViewById<ImageView>(R.id.iv_car)
        val ivmap = findViewById<ImageView>(R.id.iv_map)
        val ivuser = findViewById<ImageView>(R.id.iv_user)

        ivhome.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        ivcar.setOnClickListener {
            val intent = Intent(this, CarActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        ivmap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        ivuser.setOnClickListener {
            val intent = Intent(this, CuentaActivity::class.java)
            intent.putExtra("usuarioId", usuarioId)
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_productos, ProductosFragmentCliente())
            .commit()
    }
}

