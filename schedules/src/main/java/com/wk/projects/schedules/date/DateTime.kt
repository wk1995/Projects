package com.wk.projects.schedules.date

import java.text.SimpleDateFormat
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
object DateTime {
    private val mSimpleDateFormat by lazy { SimpleDateFormat("yy-MM-dd-HH:mm:ss:SSS", Locale.getDefault()) }
    @JvmStatic
    fun getTimeString(time: Long?): String =
            if (time == null) "null" else
                mSimpleDateFormat.format(time)

    fun getTime(timeString: String): Long = mSimpleDateFormat.parse(timeString).time

    @JvmStatic
    //获取今天0点0分0秒0毫秒
    fun getTodayStart(): Long {
        val todayStart = Calendar.getInstance()
        todayStart.set(Calendar.HOUR, 0)
        todayStart.set(Calendar.MINUTE, 0)
        todayStart.set(Calendar.SECOND, 0)
        todayStart.set(Calendar.MILLISECOND, 0)
        return todayStart.timeInMillis
    }

    @JvmStatic
    //获取今天23点59分59秒999毫秒
    fun getTodayEnd(): Long {
        val todayEnd = Calendar.getInstance()
        todayEnd.set(Calendar.HOUR, 23)
        todayEnd.set(Calendar.MINUTE, 59)
        todayEnd.set(Calendar.SECOND, 59)
        todayEnd.set(Calendar.MILLISECOND, 999)
        return todayEnd.timeInMillis
    }
}