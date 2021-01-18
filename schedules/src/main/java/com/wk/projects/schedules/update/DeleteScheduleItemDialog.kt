package com.wk.projects.schedules.update

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wk.projects.common.BaseSimpleDialog
import com.wk.projects.common.R
import com.wk.projects.common.communication.constant.BundleKey.LIST_ITEM_NAME
import com.wk.projects.common.communication.constant.IFAFlag

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
class DeleteScheduleItemDialog : BaseSimpleDialog() {
    companion object {
        fun create(bundle: Bundle? = null): DeleteScheduleItemDialog {
            val mUpdateOrDeleteDialog = DeleteScheduleItemDialog()
            mUpdateOrDeleteDialog.arguments = bundle
            return mUpdateOrDeleteDialog
        }
    }

    private lateinit var tvCommon: TextView
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnComSimpleDialogOk ->
                iFa.communication(IFAFlag.DELETE_ITEM_DIALOG, arguments)

        }
        super.onClick(v)
    }

    override fun initVSView(vsView: View) {
        tvCommon = vsView.findViewById(R.id.tvCommon)
        tvCommon.setText(com.wk.projects.schedules.R.string.schedules_delete_item)
        tvCommon.gravity= Gravity.CENTER
        view?.findViewById<TextView>(R.id.tvComSimpleDialogTheme)?.text = (arguments?.getString(LIST_ITEM_NAME))
    }

    override fun initViewSubLayout() = R.layout.common_only_text

}