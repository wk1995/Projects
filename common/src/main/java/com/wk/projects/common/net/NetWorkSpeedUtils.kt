package com.wk.projects.common.net

import android.content.Context
import android.net.TrafficStats
import android.os.Handler
import android.os.Message

import java.util.Timer
import java.util.TimerTask

/**
 * <pre>
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2018/08/14
 * desc   : 获取网速
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
</pre> *
 */
class NetWorkSpeedUtils(private val context: Context, private val mHandler: Handler) {

    private var lastTotalRxBytes: Long = 0
    private var lastTimeStamp: Long = 0

    private val timer by lazy { Timer() }

    private val task = object : TimerTask() {
        override fun run() {
            showNetSpeed()
        }
    }

    private//转为KB
    val totalRxBytes: Long
        get() =
            if (TrafficStats.getUidRxBytes(context.applicationInfo.uid) == TrafficStats.UNSUPPORTED.toLong())
                0
            else
                TrafficStats.getTotalRxBytes() / 1024

    fun startShowNetSpeed() {
        lastTotalRxBytes = totalRxBytes
        lastTimeStamp = System.currentTimeMillis()
        timer.schedule(task, 100, 100) // 1s后启动任务，每2s执行一次

    }

    fun stopNetSpeed() {
        task.cancel()
        timer.cancel()
    }

    private fun showNetSpeed() {
        val nowTotalRxBytes = totalRxBytes
        val nowTimeStamp = System.currentTimeMillis()
        val speed = (nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp)//毫秒转换
        val speed2 = (nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp)//毫秒转换

        lastTimeStamp = nowTimeStamp
        lastTotalRxBytes = nowTotalRxBytes

        val msg = mHandler.obtainMessage()
        msg.what = 100
        msg.obj = speed.toString() + "." + speed2.toString() + " kb/s"
        mHandler.sendMessage(msg)//更新界面
    }

}
