package com.wk.projects.common.location.gps

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import timber.log.Timber

class BaseLocationListener: LocationListener {
    /**
     * 当位置改变时触发
     * */
    override fun onLocationChanged(location: Location) {
        Timber.i("onLocationChanged  location  $location")

    }

    /**
     * Provider失效时触发<br/>
     * 在国内使用网络服务来定位是不可用的<br/>
     * 所以如果使用{@link LocationManager#NETWORK_PROVIDER}会触发这个方法
     * */
    override fun onProviderDisabled(arg0: String) {
        Timber.i("onProviderDisabled arg0: $arg0")

    }

    /**
     * Provider可用时触发
     * */
    override fun onProviderEnabled(arg0: String) {
        Timber.i("onProviderEnabled arg0: $arg0")
    }

    /**
     * Provider状态改变时触发
     * */
    override fun onStatusChanged(arg0: String, arg1: Int, arg2: Bundle) {
        Timber.i("onStatusChanged arg0: $arg0  arg1 : $arg1  arg2  $arg2")
    }
}