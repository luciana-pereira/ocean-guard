package com.greenconect.oceanguard

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.greenconect.oceanguard.adapters.ImageAdapter


class DashboardActivity : AppCompatActivity() {

    private lateinit var reportSpecies: Button

    private var viewPager: ViewPager2? = null
    private val images =
        intArrayOf(R.drawable.background1, R.drawable.background1, R.drawable.background1, R.drawable.background1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        viewPager = findViewById(R.id.viewPager)
        val adapter = ImageAdapter(images)
        viewPager?.adapter = adapter
    }
}
