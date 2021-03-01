package com.wk.projects.common.time.date

import java.util.*

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/27
 * desc         : 日期的工具类
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


}