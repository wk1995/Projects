package com.wk.test

import com.wk.projects.common.log.WkLog
import com.wk.projects.common.log.local.SystemPrintStrategy

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/13
 * desc         :
 */


object TestLogUtil {
    init {
        WkLog.localPrintStrategy= SystemPrintStrategy()
    }
    @JvmStatic
    fun log(msg:String){
        WkLog.d("[${getCurrentThreadName()}]  $msg","TestModule")
    }

    @JvmStatic
    fun log(msg:Any?){
        WkLog.d("[${getCurrentThreadName()}]  $msg","TestModule")
    }

    @JvmStatic
    fun log(msg:Int){
        WkLog.d("[${getCurrentThreadName()}]  $msg","TestModule")
    }


    private fun  getCurrentThreadName()=Thread.currentThread().name
}