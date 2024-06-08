package com.greenconect.oceanguard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.greenconect.oceanguard.adapters.ActivitiesAdapter
import com.greenconect.oceanguard.data.model.Activity

class FishingActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var recyclerView: RecyclerView

    private val activities = mutableListOf<Activity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fishing)

        // Inicialização do MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inicialização do RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ActivitiesAdapter(activities)
        recyclerView.adapter = adapter

        // Buscar e exibir dados
        fetchDataAndDisplay()
    }

    private fun fetchDataAndDisplay() {
        // Implemente a chamada da API para buscar dados de atividades
        // Para demonstração, estamos adicionando dados simulados
        activities.add(Activity("Fishing", "2023-01-01T12:00:00Z", 37.7749, -122.4194))
        activities.add(Activity("Illegal Fishing", "2023-01-02T15:00:00Z", 34.0522, -118.2437))

        // Notifica o adaptador sobre alterações de dados
        recyclerView.adapter?.notifyDataSetChanged()

        //Adiciona marcadores ao mapa
        for (activity in activities) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(activity.latitude, activity.longitude))
                    .title(activity.type)
            )
        }
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.7749, -122.4194), 10f))
    }

    // Handle MapView lifecycle
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}