package com.wk.projects.common.time.date

import com.wk.projects.common.constant.NumberConstants
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

    fun getDateString( time: Long?,pattern:String)=getDateString(time, SimpleDateFormat(pattern, Locale.getDefault()))

    @JvmStatic
    fun getDateLong(timeString: String, mSimpleDateFormat: SimpleDateFormat = defaultSimpleDateFormat)
            : Long = mSimpleDateFormat.parse(timeString)?.time?:NumberConstants.number_long_one_Negative

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
        if (h == 0L)
            return String.format("%02d:%02d", min, second)
        else
            return String.format("%02d:%02d:%02d", h, min, second)


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

    /**
     * 获取某一天的0点0分0秒0毫秒 月和日都从0开始算
     * */
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
     * 获取某个月的第一天的0点0分0秒0毫秒 月和日都从0开始算
     * */
    fun getMonthStart(time:Long):Long{
        val thisMonth = Calendar.getInstance()
        thisMonth.time = Date(time)
        thisMonth.set(Calendar.HOUR_OF_DAY, 0)
        thisMonth.set(Calendar.MINUTE, 0)
        thisMonth.set(Calendar.SECOND, 0)
        thisMonth.set(Calendar.MILLISECOND, 0)
        thisMonth.set(Calendar.DAY_OF_MONTH, 1)
        return thisMonth.timeInMillis
    }

    /**
     * 获取某个月的最后时刻
     * 取下一月的第一天，-1
     * */
    fun getMonthEnd(time:Long):Long{
        val thisMonth = Calendar.getInstance()
        thisMonth.time = Date(time)
        var theMonthValue=thisMonth.get(Calendar.MONTH)
        var theYearValue=thisMonth.get(Calendar.YEAR)
        thisMonth.set(Calendar.HOUR_OF_DAY, 0)
        thisMonth.set(Calendar.MINUTE, 0)
        thisMonth.set(Calendar.SECOND, 0)
        thisMonth.set(Calendar.MILLISECOND, 0)
        thisMonth.set(Calendar.DAY_OF_MONTH, 1)
        //表示12月
        if(theMonthValue==11){
            theMonthValue=0
            theYearValue++
        }else{
            theMonthValue++
        }
        thisMonth.set(Calendar.MONTH, theMonthValue)
        thisMonth.set(Calendar.YEAR, theYearValue)
        return thisMonth.timeInMillis-1
    }



    /**获取某一天的23点59分59秒999毫秒*/
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
    /**获取某一天的23点59分59秒999毫秒*/
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