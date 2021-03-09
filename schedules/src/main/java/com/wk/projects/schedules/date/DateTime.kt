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
    private val defaultSimpleDateFormat by lazy {
        SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS", Locale.getDefault())
    }

    @JvmStatic
    fun getDateString(time: Long?, mSimpleDateFormat: SimpleDateFormat = defaultSimpleDateFormat)
            : String =
            if (time == null) "null" else
                mSimpleDateFormat.format(time)

    @JvmStatic
    fun getDateString(time: Long?,dateFormatString:String):String{
        val dateFormat=SimpleDateFormat(dateFormatString, Locale.getDefault())
        return dateFormat.format(time)
    }


    @JvmStatic
    fun getDateLong(timeString: String, mSimpleDateFormat: SimpleDateFormat = defaultSimpleDateFormat)
            : Long = mSimpleDateFormat.parse(timeString).time

    /**
     * @param time 毫秒
     * @return 00:00:00时间
     * */
    @JvmStatic
    fun getTime(time: Long): String {
        var dealTime = time
        //毫秒
        var ms = 0L
        //秒
        var second = 0L
        //分
        var min = 0L
        //时
        var h = 0L
        ms = dealTime % 1000
        if (dealTime >= 1000) {
            dealTime /= 1000
            second = dealTime % 60
            if (dealTime >= 60) {
                dealTime /= 60
                min = dealTime % 60
                if (dealTime >= 60) {
                    dealTime /= 60
                    h = dealTime % 60
                    if (h >= 24)
                        return "超过一天了"
                }
            }
        }
        return if (h == 0L)
            String.format("%02d:%02d", min, second)
        else
            String.format("%02d:%02d:%02d", h, min, second)


    }


    /**
     * 获取某一天的0点0分0秒0毫秒 月和日都从0开始算
     * */
    @JvmStatic
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
    fun getDayStart(time: Long?): Long {
        if (time == null) return getDayStart()
        val todayStart = Calendar.getInstance()
        todayStart.time = Date(time)
        todayStart.set(Calendar.HOUR_OF_DAY, 0)
        todayStart.set(Calendar.MINUTE, 0)
        todayStart.set(Calendar.SECOND, 0)
        todayStart.set(Calendar.MILLISECOND, 0)
        return todayStart.timeInMillis
    }

    /**
     * 获取某一天的23点59分59秒999毫秒
     * */
    @JvmStatic
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

    @JvmStatic
    fun getDayEnd(time: Long?): Long {
        if (time == null) return getDayEnd()
        val todayEnd = Calendar.getInstance()
        todayEnd.time = Date(time)
        todayEnd.set(Calendar.HOUR_OF_DAY, 23)
        todayEnd.set(Calendar.MINUTE, 59)
        todayEnd.set(Calendar.SECOND, 59)
        todayEnd.set(Calendar.MILLISECOND, 999)
        return todayEnd.timeInMillis
    }


}