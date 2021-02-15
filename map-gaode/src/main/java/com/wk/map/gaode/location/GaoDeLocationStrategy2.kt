package com.wk.map.gaode.location

import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.wk.mao.gaode.R
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.log.WkLog

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/10
 * desc         :sdk 5.0之后的
 */


class GaoDeLocationStrategy2(val aMap: AMap) : IGaoDeLocationStrategy,AMapLocationListener {
    private var mLocationClient: AMapLocationClient = AMapLocationClient(WkProjects.getApplication())
    private val mLocationOption by lazy { AMapLocationClientOption() }
    /**定位蓝点*/
    private var locationMarker: Marker? = null
    init {
        //设置定位回调监听
        mLocationClient.setLocationListener(this)
        mLocationOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //获取一次定位结果：
        mLocationOption.isOnceLocation = true

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.isOnceLocationLatest = true
        mLocationOption.isNeedAddress = true
        mLocationOption.httpTimeOut = 20000
        mLocationClient.setLocationOption(mLocationOption)
        startLocation()
    }

    override fun startLocation() {
        stopLocation()
        mLocationClient.startLocation()
    }

    override fun stopLocation() {
        mLocationClient.stopLocation()
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        WkLog.i("AmapError", "onLocationChanged")
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //定位成功回调信息，设置相关消息
                //取出经纬度
                val latLng = LatLng(amapLocation.latitude, amapLocation.longitude)
                //添加Marker显示定位位置
                if (locationMarker == null) {
                    //如果是空的添加一个新的,icon方法就是设置定位图标，可以自定义
                    locationMarker = aMap.addMarker(MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)))
                } else {
                    //已经添加过了，修改位置即可
                    locationMarker?.position = latLng
                }

                //然后可以移动到定位点,使用animateCamera就有动画效果
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                WkLog.i("AmapError", "location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo)
            }
        }
    }
}