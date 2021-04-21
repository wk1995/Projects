package com.wk.projects.common

import android.os.Bundle
import android.view.View
import android.widget.EditText

/**
 * @author :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/03/24
 * desc         :
 */
class SimpleOnlyEtDialog : BaseSimpleDialog() {

    private var etCommon: EditText? = null
    override fun initVSView(vsView: View) {
        etCommon = vsView.findViewById(R.id.etCommon)
    }

    override fun initViewSubLayout(): Int {
        return R.layout.common_only_edit
    }

    override fun getOkBundle(): Bundle? {
        val bundle =Bundle()
        bundle.putString("textString",etCommon?.text?.toString())
        return bundle
    }

    companion object {
        fun create(bundle: Bundle?=null, simpleOnlyEtDialogListener: SimpleOnlyEtDialogListener?=null): SimpleOnlyEtDialog {
            val simpleOnlyEtDialog = SimpleOnlyEtDialog()
            simpleOnlyEtDialog.arguments = bundle
            simpleOnlyEtDialog.simpleOnlyEtDialogListener = simpleOnlyEtDialogListener
            return simpleOnlyEtDialog
        }
    }
}