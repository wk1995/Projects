package com.wk.projects.common.time.date

import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2021/5/4
 * desc   :
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 */
class DateTimeTest{

    @Test
    fun test(){
        val calendar=Calendar.getInstance()
        for (i in 0 ..20){
            calendar.set(Calendar.DAY_OF_MONTH,i)
            println(calendar.timeInMillis)
        }

    }
}