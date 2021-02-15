package com.wk.map.gaode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.wk.mao.gaode.databinding.MapGaodeMapActivityBinding
import com.wk.map.gaode.location.GaoDeLocationStrategy2
import com.wk.map.gaode.location.IGaoDeLocationStrategy
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.ui.WkToast

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/10
 * desc         :高德地图页面
 */

class GaoDeMapActivity : AppCompatActivity(), View.OnClickListener, AMap.OnMapClickListener, AMap.OnMapLongClickListener,
        GeocodeSearch.OnGeocodeSearchListener {
    companion object {
        private const val WRITE_COARSE_LOCATION_REQUEST_CODE = 1
    }

    private lateinit var mMapView: MapView
    private lateinit var aMap: AMap

    /**定位按钮*/
    private lateinit var btnLocation: Button

    private lateinit var gaoDeLocationStrategy: IGaoDeLocationStrategy

    private val geocoderSearch by lazy {
        GeocodeSearch(this)
    };

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MapGaodeMapActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mMapView = binding.map
        btnLocation = binding.btnLocation
        btnLocation.setOnClickListener(this)
        mMapView.onCreate(savedInstanceState)
        aMap = mMapView.map
        aMap.setOnMapClickListener(this)
        aMap.setOnMapLongClickListener(this)
        gaoDeLocationStrategy = GaoDeLocationStrategy2(aMap)
        startLocation()
        //初始化定位
        checkLocationPermission()


        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    override fun onClick(v: View?) {
        when (v) {
            btnLocation -> {
                startLocation()
            }
        }
    }

    /**定位*/
    private fun startLocation() {
        gaoDeLocationStrategy.startLocation()
    }

    private fun checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // 检查权限状态
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                /*
                 * 用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                 * 第一次授权失败之后，退出App再次进入时，再此处重新调出允许权限提示框
                 */
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), WRITE_COARSE_LOCATION_REQUEST_CODE)
                WkLog.d("-----get--Permissions--success--1-", "info:")
            } else {
                /*
                 * 用户未彻底拒绝授予权限
                 * 第一次安装时，调出的允许权限提示框，之后再也不提示
                 */
                WkLog.d("-----get--Permissions--success--2-", "info:")
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                        WRITE_COARSE_LOCATION_REQUEST_CODE)//自定义的code
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_COARSE_LOCATION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocation()
                } else {
                    // 申请失败
//                    WkToast.showToast( "请在设置中更改定位权限")
                }
            }

        }
    }

    override fun onMapClick(p0: LatLng?) {
        WkLog.i("onMapClick  la: ${p0?.latitude}  lng:  ${p0?.longitude}", "wk")
    }

    override fun onMapLongClick(p0: LatLng?) {
        WkLog.i("onMapLongClick  la: ${p0?.latitude}  lng:  ${p0?.longitude}", "wk")
        val latLonPoint = LatLonPoint(p0?.latitude ?: 0.0, p0?.longitude ?: 0.0)
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        val query = RegeocodeQuery(latLonPoint, 200f, GeocodeSearch.AMAP)
        geocoderSearch.getFromLocationAsyn(query)
    }

    override fun onRegeocodeSearched(result: RegeocodeResult?, rCode: Int) {
        WkLog.i("onRegeocodeSearched ")

        if (rCode == 1000) {
            if (result?.regeocodeAddress != null
                    && result.regeocodeAddress.formatAddress != null) {

                val addressName = result.regeocodeAddress.formatAddress + "附近"

                WkToast.showToast(addressName)
            } else {
                WkToast.showToast("shiasa")
            }
        } else {
            WkToast.showToast(rCode);
        }
    }


    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {
        WkLog.i("onGeocodeSearched ")
    }
}
