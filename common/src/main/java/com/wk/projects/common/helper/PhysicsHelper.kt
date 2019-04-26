package com.wk.projects.common.helper

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import com.wk.projects.common.configuration.WkProjects
import android.util.DisplayMetrics
import com.wk.projects.common.BaseProjectsActivity


/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/12
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 关于物理的一些方法：屏幕的长宽、
 * </pre>
 */
class PhysicsHelper private constructor() {

    companion object {
        object PhysicsHelperVH {
            val INSTANCE = PhysicsHelper()
        }

        fun getInstance() = PhysicsHelperVH.INSTANCE
    }

    private val application by lazy { WkProjects.getContext() }
    private val windowManager by lazy { application.getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private val mConnectivityManager by lazy { application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    private val display by lazy { windowManager.defaultDisplay }
    private var outMetrics: DisplayMetrics? = null


    fun getScreenWidth(): Int {
        if (outMetrics == null) {
            outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
        }
        return outMetrics?.widthPixels ?: 0
    }

    fun getScreenHeight(): Int {
        if (outMetrics == null) {
            outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)
        }
        return outMetrics?.heightPixels ?: 0
    }

    //跳转到系统GPS界面
    fun openSetGps(activity: BaseProjectsActivity) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivityForResult(intent, 0)
    }

    //判断是否有网络连接
    fun isNetWorkConnected() =
            mConnectivityManager.activeNetworkInfo?.isAvailable == true

    //判断WIFI网络是否可用
    fun isWifiConnected()=mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isAvailable==true

    //判断MOBILE网络是否可用
    fun isMobileConnected()=mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isAvailable==true

    //判断是否有该权限
    fun hasPermission(permission: String) =
            ContextCompat.checkSelfPermission(application,
                    permission) ==
                    PackageManager.PERMISSION_GRANTED


}