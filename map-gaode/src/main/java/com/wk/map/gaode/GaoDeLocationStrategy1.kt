package com.wk.map.gaode

import android.location.Location
import com.amap.api.maps.AMap
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.MyLocationStyle
import com.wk.projects.common.log.WkLog

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/10
 * desc         :sdk 5.0之后的
 */


class GaoDeLocationStrategy1(val aMap: AMap) : IGaoDeLocationStrategy , AMap.OnMyLocationChangeListener{
    private val myLocationStyle by lazy {
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        val myLocationStyle = MyLocationStyle()
//        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只定位一次。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus))
        myLocationStyle
    }

    init {
        aMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this)
    }


    override fun startLocation() {
        aMap.myLocationStyle = myLocationStyle
    }

    override fun stopLocation() {
        aMap.myLocationStyle =null
    }

    override fun onMyLocationChange(p0: Location?) {
        WkLog.i("onMyLocationChange ", "wk")
    }
}