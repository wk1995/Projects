package com.wk.projects.schedules.date

import java.text.SimpleDateFormat
import java.time.Year
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
    private val mSimpleDateFormat by lazy { SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS", Locale.getDefault()) }
    @JvmStatic
    fun getTimeString(time: Long?): String =
            if (time == null) "null" else
                mSimpleDateFormat.format(time)

    @JvmStatic
    fun getTime(timeString: String): Long = mSimpleDateFormat.parse(timeString).time

    @JvmStatic
    //获取某一天的0点0分0秒0毫秒 月和日都从0开始算
    fun getDayStart(day: Int? = null, month: Int? = null, year: Int? = null): Long {
        val todayStart = Calendar.getInstance()
        if (year != null)
            todayStart.set(Calendar.YEAR, year)
        if (month != null)
            todayStart.set(Calendar.MONTH, month)
        if (day != null)
            todayStart.set(Calendar.DAY_OF_MONTH, day)
        todayStart.set(Calendar.HOUR_OF_DAY, 0)
        todayStart.set(Calendar.MINUTE, 0)
        todayStart.set(Calendar.SECOND, 0)
        todayStart.set(Calendar.MILLISECOND, 0)
        return todayStart.timeInMillis
    }

    @JvmStatic
    //获取某一天的23点59分59秒999毫秒
    fun getDayEnd(day: Int? = null, month: Int? = null, year: Int? = null): Long {
        val todayEnd = Calendar.getInstance()
        if (year != null)
            todayEnd.set(Calendar.YEAR, year)
        if (month != null)
            todayEnd.set(Calendar.MONTH, month)
        if (day != null)
            todayEnd.set(Calendar.DAY_OF_MONTH, day)
        todayEnd.set(Calendar.HOUR_OF_DAY, 23)
        todayEnd.set(Calendar.MINUTE, 59)
        todayEnd.set(Calendar.SECOND, 59)
        todayEnd.set(Calendar.MILLISECOND, 999)
        return todayEnd.timeInMillis
    }
}