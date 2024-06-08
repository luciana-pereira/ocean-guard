package com.greenconect.oceanguard

import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
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
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FishingActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var recyclerView: RecyclerView
    private lateinit var captureButton: Button
    private lateinit var reportButton: Button
    private lateinit var backButton: Button
    private lateinit var capturedImageUri: Uri

    private val activities = mutableListOf<Activity>()
    private val REQUEST_IMAGE_CAPTURE = 1

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

        // Inicialização dos botões
        captureButton = findViewById(R.id.buttonCapture)
        reportButton = findViewById(R.id.buttonReport)
        backButton = findViewById(R.id.buttonBack)

        captureButton.setOnClickListener { dispatchTakePictureIntent() }
        reportButton.setOnClickListener { openReportActivity() }
        backButton.setOnClickListener { finish() }
    }

    private fun fetchDataAndDisplay() {
        // Implemente a chamada da API para buscar dados de atividades
        // Para demonstração, estamos adicionando dados simulados
        activities.add(Activity("Fishing", "2023-01-01T12:00:00Z", 37.7749, -122.4194))
        activities.add(Activity("Illegal Fishing", "2023-01-02T15:00:00Z", 34.0522, -118.2437))

        // Notifica o adaptador sobre alterações de dados
        recyclerView.adapter?.notifyDataSetChanged()

        // Adiciona marcadores ao mapa
        for (activity in activities) {
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(activity.latitude, activity.longitude))
                    .title(activity.type)
            )
        }
    }

    // Captura de imagem
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.greenconect.oceanguard.fileprovider",
                        it
                    )
                    capturedImageUri = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            return ;
            // Criar uma função handle para manipular a imagem capturada, se necessário.
        }
    }

    // Método para abrir o formulário de denúncia:
    private fun openReportActivity() {
        val intent = Intent(this, ReportIllegalActivity::class.java)
        startActivity(intent)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.7749, -122.4194), 10f))
        fetchDataAndDisplay()
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