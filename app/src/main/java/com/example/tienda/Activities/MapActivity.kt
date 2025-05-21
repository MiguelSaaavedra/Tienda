package com.example.tienda.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tienda.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var usuarioId: Int = -1
    private lateinit var mMap: GoogleMap

    private val ubicacionTienda = LatLng(4.5778985, -74.2125086)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


        usuarioId = intent.getIntExtra("usuarioId", -1)
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

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        moveToLocation(ubicacionTienda, "Nuestra tienda fisica")
    }

    private fun moveToLocation(location: LatLng, title: String) {

        mMap.clear()
        mMap.addMarker(MarkerOptions().position(location).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
    }

}

