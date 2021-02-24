package com.wk.projects.common

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wk.projects.common.communication.IFragmentToActivity
import com.wk.projects.common.log.WkLog

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
abstract class BaseProjectsActivity : AppCompatActivity(), IFragmentToActivity,
        View.OnClickListener,View.OnLongClickListener {
    companion object {
        private const val TAG = "BaseProjectsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        val resLayout=initResLayId()
        if(resLayout is Int ){
            setContentView(resLayout)
        }
        if(resLayout is View){
            setContentView(resLayout)
        }
        bindView(savedInstanceState, this)
    }

    override fun onStart() {
        super.onStart()
        WkLog.d("${this::class.java.simpleName} onStart")
    }

    override fun onResume() {
        super.onResume()
        WkLog.d("${this::class.java.simpleName} onResume")
    }

    override fun onRestart() {
        super.onRestart()
        WkLog.d("${this::class.java.simpleName} onRestart")
    }



    override fun onPause() {
        super.onPause()
        WkLog.d("${this::class.java.simpleName} onPause")
    }

    override fun onStop() {
        super.onStop()
        WkLog.d("${this::class.java.simpleName} onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        WkLog.d("${this::class.java.simpleName} onDestroy")
    }


    override fun communication(flag: Int, bundle: Bundle?, any: Any?) {}

    open fun initContentView() {}
    override fun onClick(v: View?) {
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    abstract fun initResLayId(): Any
    abstract fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity)
}