package com.wk.test.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2022/4/13
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class FlowTest {
    private val intFlow = flow {
        (1..3).forEach {
            println("emit $it")
            emit(it)
            delay(100)
        }
    }


    fun way(){
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
}