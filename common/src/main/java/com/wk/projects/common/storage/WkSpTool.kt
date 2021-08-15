package com.wk.projects.common.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/5/6
 * desc   :
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 */
internal class WkSpTool private constructor(private val context: Context) {
    fun getInstance(context: Context): WkSpTool? {
        if (singleton == null) {
            synchronized(WkSpTool::class.java) {
                if (singleton == null) {
                    singleton = WkSpTool(context)
                }
            }
        }
        return singleton
    }

    private var defaultSp: SharedPreferences? = null
    private fun initDefaultSp() {
        if (defaultSp == null) {
            defaultSp = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    fun put(key:String,value:String,fileName:String?=null) {}

    companion object {
        @Volatile
        private var singleton: WkSpTool? = null
    }
}