package com.wk.test.coroutines.flow

import com.wk.test.TestLogUtil
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 *
 * author : wk
 * e-mail : 1226426603@qq.com
 * time   : 2022/4/21
 * desc   :
 * GitHub : https://github.com/wk1995
 * CSDN   : http://blog.csdn.net/qq_33882671
 */
class CoroutinesShareFlowViewModelTest {
    private val retryShareFlow = MutableSharedFlow<Int>(2)
    private val shareFlow = MutableSharedFlow<Int>()
    private val extraBufferCapacityShareFlow = MutableSharedFlow<Int>(extraBufferCapacity = 2)
    private val dropOldestShareFlow =
        MutableSharedFlow<Int>(extraBufferCapacity = 2,onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val dropLatestShareFlow =
        MutableSharedFlow<Int>(extraBufferCapacity = 2, onBufferOverflow = BufferOverflow.DROP_LATEST)

    @Test
    fun test() {
        shareFlow()
    }

    fun dropLatestShareFlow(){
        runBlocking {
            TestLogUtil.log("runBlocking start")
            launch {
                TestLogUtil.log("collect start")
                dropLatestShareFlow.collect {
                    TestLogUtil.log("shareFlow collect: $it")
                }
                TestLogUtil.log("collect end")
            }
            launch {
                (1..10).forEach {
                    TestLogUtil.log(
                        "shareFlow emit: $it"
                    )
                    dropLatestShareFlow.emit(it)
                }
            }
            TestLogUtil.log("runBlocking end")
        }
    }

    fun dropOldestShareFlow(){
        runBlocking {
            TestLogUtil.log("runBlocking start")
            launch {
                TestLogUtil.log("collect start")
                dropOldestShareFlow.collect {
                    TestLogUtil.log("shareFlow collect: $it")
                }
                TestLogUtil.log("collect end")
            }
            launch {
                (1..10).forEach {
                    TestLogUtil.log(
                        "shareFlow emit: $it"
                    )
                    dropOldestShareFlow.emit(it)
                }
            }
            TestLogUtil.log("runBlocking end")
        }
    }


    private fun hasExtraBufferCapacity(){
        runBlocking {
            TestLogUtil.log("runBlocking start")
            launch {
                TestLogUtil.log("collect start")
                extraBufferCapacityShareFlow.collect {
                    TestLogUtil.log("shareFlow collect: $it")
                }
                TestLogUtil.log("collect end")
            }
            launch {
                (1..10).forEach {
                    TestLogUtil.log(
                        "shareFlow emit: $it"
                    )
                    extraBufferCapacityShareFlow.emit(it)
                }
            }
            TestLogUtil.log("runBlocking end")
        }
    }

    private fun noRetry() {
        runBlocking {
            TestLogUtil.log("runBlocking start")
            launch {
                (1..10).forEach {
                    TestLogUtil.log(
                        "shareFlow emit: $it"
                    )
                    shareFlow.emit(it)
                }
            }
            TestLogUtil.log("runBlocking go on")
            launch {
                TestLogUtil.log("collect start")
                shareFlow.collect {
                    delay(
                        100
                    )
                    TestLogUtil.log("shareFlow collect: $it")
                }
                TestLogUtil.log("collect end")
            }
            TestLogUtil.log("runBlocking end")
        }

    }


    private fun shareFlow() {
        runBlocking {
            TestLogUtil.log("runBlocking start")
            launch {
                TestLogUtil.log("collect start")
                shareFlow.collect {
                    TestLogUtil.log(
                        "shareFlow collect: $it"
                    )
                }
                TestLogUtil.log("collect end")
            }
            TestLogUtil.log("runBlocking go on")
            launch {
                (1..10).forEach {
                    TestLogUtil.log("shareFlow emit: $it")
                    shareFlow.emit(it)
                }
            }
            TestLogUtil.log("runBlocking end")
        }
    }


    private fun hasRetry() {
        runBlocking {
            TestLogUtil.log("runBlocking start")
            launch {
                (1..10).forEach {
                    TestLogUtil.log("shareFlow emit: $it")
                    retryShareFlow.emit(it)
                }
            }
            TestLogUtil.log("runBlocking go on")
            launch {
                TestLogUtil.log("collect start")
                retryShareFlow.collect {
                    delay(100)
                    TestLogUtil.log("shareFlow collect: $it")
                }
                TestLogUtil.log("collect end")
            }
            TestLogUtil.log("runBlocking end")
        }

    }
}


