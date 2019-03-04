package com.wk.projects.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.wk.projects.common.communication.IFragmentToActivity

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/23
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
abstract class BaseProjectsActivity: AppCompatActivity(),IFragmentToActivity {

    private lateinit var activityUnBinder: Unbinder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        setContentView(initResLayId())
        activityUnBinder=ButterKnife.bind(this)
        bindView(savedInstanceState,this)
    }

    override fun onDestroy() {
        activityUnBinder.unbind()
        super.onDestroy()
    }


    override fun communication(flag:Int,bundle: Bundle?, any: Any?){}

    open fun initContentView(){}


    abstract fun initResLayId():Int
    abstract fun bindView(savedInstanceState: Bundle?,mBaseProjectsActivity:BaseProjectsActivity)
}