package com.wk.projects.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import butterknife.ButterKnife
import butterknife.Unbinder
import com.wk.projects.common.communication.IFragmentToActivity

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/24
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
abstract class BaseDialogFragment : DialogFragment() {
    private lateinit var activityUnBinder: Unbinder
    protected lateinit var mActivity: BaseProjectsActivity
    protected lateinit var iFa:IFragmentToActivity


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as BaseProjectsActivity
        iFa=mActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        hideBottomUIMenu()
        val rootView = inflater.inflate(initResLayId(), container, false)
        activityUnBinder = ButterKnife.bind(this, rootView)
        bindView(savedInstanceState, rootView)
        return rootView
    }

    override fun onDestroyView() {
        activityUnBinder.unbind()
        super.onDestroyView()
    }

    /**
     * 隐藏虚拟键
     */
    private fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        val v = dialog.window.decorView
        if (Build.VERSION.SDK_INT in 11..18) { // lower api

            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.

            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            v.systemUiVisibility = uiOptions

        }

    }

    abstract fun initResLayId(): Int
    abstract fun bindView(savedInstanceState: Bundle?, rootView: View?)
}