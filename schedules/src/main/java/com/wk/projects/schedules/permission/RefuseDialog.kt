package com.wk.projects.schedules.permission

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.R

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/28
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
 class RefuseDialog : BaseSimpleDialog() {


    lateinit var tvCommon: TextView
    override fun bindView(savedInstanceState: Bundle?) {
        super.bindView(savedInstanceState)
        view?.findViewById<TextView>(R.id.btnComSimpleDialogCancel)?.visibility=View.GONE
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)

    }

    override fun initVSView(vsView: View) {
        tvCommon = vsView.findViewById(R.id.tvCommon)
        tvCommon.text="拒绝存储权限,APP退出"
    }

    override fun initViewSubLayout() = R.layout.common_only_text


}