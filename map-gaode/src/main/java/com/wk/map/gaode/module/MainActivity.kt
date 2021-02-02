package com.wk.map.gaode.module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amap.api.maps.MapView
import com.wk.map.gaode.R
import com.wk.map.gaode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mMapView:MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mMapView=binding.map
        mMapView.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }
}