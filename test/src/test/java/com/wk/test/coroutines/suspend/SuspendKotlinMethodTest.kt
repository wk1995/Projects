package com.wk.test.coroutines.suspend

import com.wk.test.TestLogUtil
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/13
 * desc         :
 */


class SuspendKotlinMethodTest {
    private val mSuspendKotlinMethod = SuspendKotlinMethod()

    @Test
    fun way() {
        runBlocking {
            try {
                TestLogUtil.log("way: 111")
                TestLogUtil.log("way: ${mSuspendKotlinMethod.returnImmediately()}")
                TestLogUtil.log("way: 1222")

            }catch(e:Exception){
                TestLogUtil.log("e: ${e.message}")
            }
        }
    }

    fun test1(){
        runBlocking {
            try {
                TestLogUtil.log("way: 111")
                TestLogUtil.log("way: ${mSuspendKotlinMethod.returnImmediately1()}")
                TestLogUtil.log("way: 1222")

            }catch(e:Exception){
                TestLogUtil.log("e: ${e.message}")
            }
        }
    }

    fun execute() {
        mSuspendKotlinMethod.execute()
    }
}