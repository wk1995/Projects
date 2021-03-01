package com.wk.projects.common.time.date.week

import java.util.*

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/27
 * desc         :
 */


object WeekUtil {
    @JvmStatic
    private fun getWeek(calenderDayOfWeekIndex: Int): Week {
        return when (calenderDayOfWeekIndex) {
            Calendar.SUNDAY -> {
                Week.Sunday
            }
            Calendar.MONDAY -> {
                Week.Monday
            }
            Calendar.TUESDAY -> {
                Week.Tuesday
            }
            Calendar.WEDNESDAY -> {
                Week.Wednesday
            }
            Calendar.THURSDAY -> {
                Week.Thursday
            }
            Calendar.FRIDAY -> {
                Week.Friday
            }
            Calendar.SATURDAY -> {
                Week.Saturday
            }
            else -> {
                Week.unknow
            }
        }
    }

    /**根据日期来获取星期*/
    @JvmStatic
    fun getWeek(date: Date): Week {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return getWeek(calendar.get(Calendar.DAY_OF_WEEK))
    }
}