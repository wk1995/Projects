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
    interface SimpleOnlyEtDialogListener {
        /**
         * @return 是否拦截 true，拦截，不实用父方法
         */
        fun ok(textString: String?): Boolean

        /**
         * @return 是否拦截 true，拦截，不实用父方法
         */
        fun cancel(): Boolean
    }

    var simpleOnlyEtDialogListener: SimpleOnlyEtDialogListener? = null
    private var etCommon: EditText? = null
    override fun initVSView(vsView: View) {
        etCommon = vsView.findViewById(R.id.etCommon)
    }

    override fun initViewSubLayout(): Int {
        return R.layout.common_only_edit
    }


    override fun ok() {
        if (simpleOnlyEtDialogListener?.ok(etCommon?.text?.toString())!=false) {
            return
        }
        super.ok()
    }

    override fun cancel() {
        if (simpleOnlyEtDialogListener?.cancel()!=false) {
            return
        }
        super.cancel()
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