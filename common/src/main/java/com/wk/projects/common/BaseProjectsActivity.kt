package com.wk.projects.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import butterknife.Unbinder
import com.wk.projects.common.communication.eventBus.RxBus
import me.yokeyword.fragmentation.SupportActivity

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/23
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : BaseProjectsActivity所有Activity父类
 * </pre>
 */
abstract class BaseProjectsActivity : SupportActivity() {

    private lateinit var activityUnBinder: Unbinder
    protected val rxBus by lazy { RxBus.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeContentView()
        val resLay = initResLay()
        when (resLay) {
            is View -> {
                setContentView(resLay)
                activityUnBinder = ButterKnife.bind(this)
                bindView(savedInstanceState, this)
            }
            is Int -> {
                setContentView(resLay)
                activityUnBinder = ButterKnife.bind(this)
                bindView(savedInstanceState, this)
            }
        }
    }


    override fun onDestroy() {
        activityUnBinder.unbind()
        super.onDestroy()
    }


    open fun beforeContentView() {}


    abstract fun initResLay(): Any
    abstract fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity)
}