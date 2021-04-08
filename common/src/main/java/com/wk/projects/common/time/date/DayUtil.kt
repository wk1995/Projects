package com.wk.projects.common.time.date

import java.util.*

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/27
 * desc         : 日期的工具类
 *
 */

object DayUtil {

    fun getDayOfMonth(date: Date): Int {
        val calendar = resetCalendar(date)
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * 周日算第一天
     * */
    fun getDayOfWeek(date: Date): Int {
        val calendar = resetCalendar(date)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    private fun resetCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }


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