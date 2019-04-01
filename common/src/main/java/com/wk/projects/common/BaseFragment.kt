package com.wk.projects.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.wk.projects.common.communication.eventBus.RxBus
import me.yokeyword.fragmentation.SupportFragment

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
abstract class BaseFragment : SupportFragment() {
    protected val rxBus by lazy { RxBus.getInstance() }
    protected val aRouter:ARouter by lazy { ARouter.getInstance() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        beforeCreateView()
        val resLay = initResLay()
        return when (resLay) {
            is Int -> inflater.inflate(resLay, container, false)
            is View -> resLay
            else -> super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        beforeViewCreated()
        super.onViewCreated(view, savedInstanceState)

    }

    abstract fun initResLay(): Any?
    protected open fun beforeCreateView() {}
    protected open fun beforeViewCreated() {}
}