package com.wk.map.gaode.module

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.wk.map.gaode.R
import com.wk.map.gaode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AMapLocationListener,
        View.OnClickListener{

    private lateinit var mMapView: MapView
    private lateinit var aMap: AMap
    /**定位按钮*/
    private lateinit var btnLocation: Button


    private lateinit var mLocationClient: AMapLocationClient

    private  val mLocationOption  by lazy { AMapLocationClientOption()  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mMapView = binding.map
        btnLocation = binding.btnLocation
        btnLocation.setOnClickListener(this)
        mMapView.onCreate(savedInstanceState)
        aMap = mMapView.map
        //初始化定位
        mLocationClient = AMapLocationClient(applicationContext)
        //设置定位回调监听
        mLocationClient.setLocationListener(this)
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    override fun onClick(v: View?) {
        when(v){
            btnLocation -> {
                mLocationClient.stopLocation();
                mLocationClient.startLocation();
            }
        }
    }
    private var locationMarker:Marker?=null
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        Log.i("AmapError", "onLocationChanged")
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息

                //取出经纬度

                //定位成功回调信息，设置相关消息

                //取出经纬度
                val latLng = LatLng(amapLocation.latitude, amapLocation.longitude)

                //添加Marker显示定位位置

                //添加Marker显示定位位置
                if (locationMarker == null) {
                    //如果是空的添加一个新的,icon方法就是设置定位图标，可以自定义
                    locationMarker = aMap.addMarker(MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)))
                } else {
                    //已经添加过了，修改位置即可
                    locationMarker?.setPosition(latLng)
                }

                //然后可以移动到定位点,使用animateCamera就有动画效果

                //然后可以移动到定位点,使用animateCamera就有动画效果
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
