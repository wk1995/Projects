package com.wk.projects.schedules.permission

import android.view.View
import android.widget.TextView
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.R
import permissions.dispatcher.PermissionRequest

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
class PermissionDialog : BaseSimpleDialog() {


    private var request: PermissionRequest? = null

    private lateinit var tvCommon: TextView

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnComSimpleDialogOk -> request?.proceed()
            R.id.btnComSimpleDialogCancel -> {
                request?.cancel()
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            }
        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
        tvCommon = vsView.findViewById(R.id.tvCommon)
        tvCommon.text = "现无存储权限,取消APP将退出"
    }

    override fun initViewSubLayout() = R.layout.common_only_text

    fun withRequest(request: PermissionRequest): PermissionDialog {
        this.request = request
        return this
    }

}