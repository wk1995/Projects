package com.wk.test.coroutines.suspend

import com.wk.test.TestLogUtil
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.intrinsics.*


/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2022/04/12
 * desc         :
 */


class SuspendKotlinMethod {


    /**
     * 最简单的suspend 方法，可以吧delay注释调，然后反编译查看该方法
     * */
    suspend fun onlySuspendSimpleMethod() {
        TestLogUtil.log("")
        delay(1000)
    }

    /**
     * 普通方法转成 suspend方法 ：接口里面只有一个方法
     * */
    suspend fun suspendJavaMethodCallbackOneMethod(javaMethod: JavaMethod) =
        suspendCoroutine<Any> { continuation ->
            javaMethod.javaSimpleCallBackMethod {
                continuation.resume(it)
            }
        }

    /**
     * 普通方法转成 suspend方法 ：接口里面只有两个方法
     * */
    suspend fun suspendJavaMethodCallbackTwoMethod(javaMethod: JavaMethod): Any? {
        return suspendCoroutine {
            val javaInterfaceTwoMethod = object : JavaMethod.IJavaInterfaceTwoMethod {
                override fun oneMethod(`object`: Any?) {
                    it.resume(`object`)
                }

                override fun twoMethod(`object`: Any?) {
                    it.resumeWithException(Exception("twoMethod"))
                }
            }
            javaMethod.javaSimpleCallBackMethod(javaInterfaceTwoMethod)


        }
    }
    /**
     * 普通方法转成 方法 ：接口里面只有三个方法
     * */
    fun suspendJavaMethodCallbackThreeMethod(javaMethod: JavaMethod): Flow<Any?>{
        return callbackFlow {
            javaMethod.javaSimpleCallBackMethod(object : JavaMethod.IJavaInterfaceThreeMethod {
                override fun oneMethod(`object`: Any?) {
                    trySendBlocking(`object`)
                }

                override fun twoMethod(`object`: Any?) {

                }

                override fun threeMethod(`object`: Any?) {

                }
            })
        }
    }

//     suspend fun javaSuspendMethod(javaMethod: JavaMethod)=javaMethod.javaSuspendMethod()


    suspend fun returnSuspend()=
        suspendCoroutineUninterceptedOrReturn<Int>{
                continuation ->
            TestLogUtil.log("returnSuspend 1")
            thread {
                Thread.sleep(1000)
                TestLogUtil.log(" returnSuspend  2")
                continuation.resume(1024)
            }
            TestLogUtil.log("returnSuspend  3")
            COROUTINE_SUSPENDED
        }


    suspend fun returnImmediately()=
        suspendCoroutineUninterceptedOrReturn<String>{
            TestLogUtil.log("returnImmediately 1")
            "return Immediately"
        }

    suspend fun returnImmediately1()=
        suspendCoroutineUninterceptedOrReturn<String>{continuation->
            TestLogUtil.log("returnImmediately 1")
            thread {
                TestLogUtil.log(" thread  start")
                TestLogUtil.log(" returnSuspend  2")
                continuation.resume("1024")
            }
            "return Immediately"
        }

    fun execute(){
        runBlocking {
            TestLogUtil.log(1)
            TestLogUtil.log("returnSuspend ${returnSuspend()}")
            TestLogUtil.log(2)
            delay(1000)
            TestLogUtil.log(3)
            TestLogUtil.log(returnImmediately())
            TestLogUtil.log(4)
        }
    }

}