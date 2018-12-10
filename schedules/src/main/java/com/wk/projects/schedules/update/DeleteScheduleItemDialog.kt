package com.wk.projects.schedules.update

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.wk.projects.common.BaseSimpleDialogFragment
import com.wk.projects.common.R
import com.wk.projects.common.communication.constant.IFAFlag
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.data.ScheduleItem
import org.litepal.LitePal

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class DeleteScheduleItemDialog : BaseSimpleDialogFragment() {
    companion object {
        fun create(bundle: Bundle? = null): DeleteScheduleItemDialog {
            val mUpdateOrDeleteDialog = DeleteScheduleItemDialog()
            mUpdateOrDeleteDialog.arguments = bundle
            return mUpdateOrDeleteDialog
        }
    }

    private lateinit var tvCommon: TextView
    override fun onClick(v: View?) {
        when (v) {
            btnComSimpleDialogOk ->
                iFa.communication(IFAFlag.DELETE_ITEM_DIALOG, arguments)

        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
        tvCommon = vsView.findViewById(R.id.tvCommon)
        tvCommon.setText(com.wk.projects.schedules.R.string.schedules_delete_item)
    }

    override fun initViewSubLayout() = R.layout.common_only_text

}