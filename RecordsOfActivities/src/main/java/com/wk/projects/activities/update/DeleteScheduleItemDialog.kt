package com.wk.projects.activities.update

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wk.projects.activities.communication.ActivitiesMsg
import com.wk.projects.common.ui.dialog.BaseSimpleDialog
import com.wk.projects.common.R
import com.wk.projects.common.communication.constant.BundleKey.LIST_ITEM_NAME
import com.wk.projects.common.communication.eventBus.EventMsg

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   : 删除的dialog
 * </pre>
 */
class DeleteScheduleItemDialog : BaseSimpleDialog() {
    companion object {
        fun create(bundle: Bundle? = null): DeleteScheduleItemDialog {
            val mUpdateOrDeleteDialog = DeleteScheduleItemDialog()
            mUpdateOrDeleteDialog.arguments = bundle
            return mUpdateOrDeleteDialog
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk ->
                rxBus.post(ActivitiesMsg(EventMsg.DELETE_ITEM_DIALOG, arguments))

        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
       val tvCommon = vsView.findViewById<TextView>(R.id.tvCommon)
        tvCommon.setText(com.wk.projects.activities.R.string.schedules_delete_item)
        tvCommon.gravity = Gravity.CENTER
        tvComSimpleDialogTheme.text = (arguments?.getString(LIST_ITEM_NAME))
    }

    override fun initViewSubLayout() = R.layout.common_only_text

}