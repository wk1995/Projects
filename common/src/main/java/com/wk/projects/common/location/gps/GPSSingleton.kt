package com.wk.projects.common.location.gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.ui.notification.ToastUtil
import timber.log.Timber

class GPSSingleton private constructor() {
    private val mContent by lazy { WkProjects.getContext() }
    private val locationManager by lazy { mContent.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private val mBaseLocationListener by lazy { BaseLocationListener() }

    companion object {
        private object GPSSingletonVH {
            val INSTANCE = GPSSingleton()

        }

        fun getInstance() = GPSSingletonVH.INSTANCE
    }


    fun getLocation() {
        val check = ContextCompat.checkSelfPermission(mContent, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (check == PackageManager.PERMISSION_GRANTED) {
            // 定义Criteria对象
            val criteria = Criteria()
            // 设置定位精确度 Criteria.ACCURACY_COARSE 比较粗略， Criteria.ACCURACY_FINE则比较精细
            criteria.accuracy = Criteria.ACCURACY_FINE
            // 设置是否需要海拔信息 Altitude
            criteria.isAltitudeRequired = false
            // 设置是否需要方位信息 Bearing
            criteria.isBearingRequired = false
            // 设置是否允许运营商收费
            criteria.isCostAllowed = true
            // 设置对电源的需求
            criteria.powerRequirement = Criteria.POWER_LOW

            // 获取GPS信息提供者
            val bestProvider = locationManager.getBestProvider(criteria, true)
            val bestProvider1=LocationManager.NETWORK_PROVIDER
            Timber.i("bestProvider = $bestProvider")
            // 获取定位信息
            val location = locationManager.getLastKnownLocation(bestProvider)
            Timber.i("location: $location  经度： ${location?.longitude}  纬度：  ${location?.latitude}")
            // 500毫秒更新一次，忽略位置变化
            locationManager.requestLocationUpdates(bestProvider, 0, 0f, mBaseLocationListener)
            locationManager.requestLocationUpdates(bestProvider1, 0, 0f, mBaseLocationListener)
        } else
            Timber.i("没有Gps相关权限")
    }

    fun getLocation1() {
        val check = ContextCompat.checkSelfPermission(mContent, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (check == PackageManager.PERMISSION_GRANTED) {
            val providers = locationManager.allProviders

            providers.forEach {
                Timber.i("provider: $it")
                val location = locationManager.getLastKnownLocation(it)
                ToastUtil.show("location: $location  经度： ${location?.longitude}  纬度：  ${location?.latitude}")
                Timber.i("location: $location  经度： ${location?.longitude}  纬度：  ${location?.latitude}")
                // 500毫秒更新一次，忽略位置变化
                locationManager.requestLocationUpdates(it, 0, 0f, mBaseLocationListener)
            }

        } else
            Timber.i("没有Gps相关权限")
    }

    fun remove(){
//        locationManager.locat
    }
}