package com.wk.projects.common.communication.eventBus

import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 事件总线
 * </pre>
 */
class RxBus private constructor() {
    companion object {
        private object RxBusVH {
            val INSTANCE = RxBus()
        }

        fun getInstance() = RxBusVH.INSTANCE
    }

    private val mBus by lazy { SerializedSubject<Any, Any>(PublishSubject.create()) }


    fun getObserverable() = mBus

    fun post(any: Any?) {
        mBus.onNext(any)
    }
}