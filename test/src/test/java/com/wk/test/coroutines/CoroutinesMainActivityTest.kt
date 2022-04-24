package com.wk.test.coroutines

import com.wk.test.TestLogUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine

/**
 *
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2022/4/12
 * desc   :
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 */
class CoroutinesMainActivityTest{
    val intFlow = flow {
        (1..3).forEach {
            println("emit $it")
            emit(it)
            delay(100)
        }
    }

    @Test
    fun way(){
        runBlocking {
            way4()
        }
    }


    fun createSuspend(){
        val continuation= suspend {
            TestLogUtil.log("in Continuation")
            2
        }.createCoroutine(object : Continuation<Int>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                TestLogUtil.log("Continuation End: $result")
            }
        })

    }

    suspend fun way4(){
        flow{
            List(100){
                emit(it)
            }
        }.conflate().collect {value->
            println("Collecting $value")
            delay(100)
            println("$value collected")
        }
    }


    fun way1(){
        println("way: start")
        runBlocking {
            intFlow.collect {
                println("collect1: $it")
            }
            intFlow.collect {
                println("collect2: $it")
            }
        }
        println("way: end")
    }


    fun createFlow() = flow<Int> {
        (1..3).forEach {
            println("emit $it")
            emit(it)
            delay(100)
        }
    }.onEach { println("onEach $it") }

    @Test
    fun way2(){
        runBlocking {
            println("collect")
            val flow= createFlow()
            flow.collect()
            flow.collect()
        }
    }
    @Test
    fun way3(){
        createFlow().launchIn(GlobalScope)
        Thread.sleep(1000)
    }




}