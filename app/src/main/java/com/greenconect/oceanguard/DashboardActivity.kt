package com.greenconect.oceanguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.greenconect.oceanguard.adapters.ImageAdapter


class DashboardActivity : AppCompatActivity() {

    private lateinit var buttonReportSpecies: Button
    private lateinit var buttonFishingMonitoring: Button
    private lateinit var buttonCommunities: Button
    private lateinit var buttonNews: Button
    private lateinit var buttonAlert: Button
    private lateinit var buttonEvents: Button

    private var viewPager: ViewPager2? = null
    private val images =
        intArrayOf(R.drawable.banner_one, R.drawable.banner_two)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewPager = findViewById(R.id.viewPager)
        val adapter = ImageAdapter(images)
        viewPager?.adapter = adapter

        initializeComponents()
        setupListeners()
    }

    // Inicializa o componentes de tela
    private fun initializeComponents() {
        buttonReportSpecies = findViewById(R.id.report)
        buttonFishingMonitoring = findViewById(R.id.monitoring)
        buttonCommunities = findViewById(R.id.communities)
        buttonNews = findViewById(R.id.news)
        buttonAlert = findViewById(R.id.alert)
        buttonEvents = findViewById(R.id.events)
    }

    // Função para identificar quando botão e pressionado
    private fun setupListeners() {
        buttonReportSpecies.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }

        buttonFishingMonitoring.setOnClickListener {
            val intent = Intent(this, FishingActivity::class.java)
            startActivity(intent)
        }
    }
}
