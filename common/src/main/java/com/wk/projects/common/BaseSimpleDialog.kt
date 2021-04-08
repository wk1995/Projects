package com.wk.projects.common

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wk.projects.common.log.WkLog
import kotlinx.android.synthetic.main.common_dialog_fragment_base_simple.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/17
 *      desc   : 最简单的DialogFragment:上下结构，标题，ViewStub，两个按钮
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>*/


abstract class BaseSimpleDialog : BaseDialogFragment(), View.OnClickListener {

    final override fun initResLayId() = R.layout.common_dialog_fragment_base_simple

    override fun bindView(savedInstanceState: Bundle?) {
        initView()
        initListener()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnComSimpleDialogOk -> {
                ok()
            }
            R.id.btnComSimpleDialogCancel -> {
                cancel()
            }
        }
    }

    open fun ok() {
        disMiss()
    }

    open fun cancel() {
        disMiss()
    }

    protected fun disMiss() {
        if (dialog?.isShowing == true) {
            dismiss()
        }
    }

    fun show(manager: FragmentManager?) {
        super.show(manager, this::class.java.simpleName)
    }

    fun show(baseProjectsActivity: BaseProjectsActivity) {
        show(baseProjectsActivity.supportFragmentManager)
    }

    //加载ViewSub中的View
    abstract fun initVSView(vsView: View)

    //设置ViewSub被替换的布局
    abstract fun initViewSubLayout(): Int


    private fun initListener() {
        btnComSimpleDialogOk.setOnClickListener(this)
        btnComSimpleDialogCancel.setOnClickListener(this)
    }

    private fun initView() {
        vsComSimpleDialogContent.layoutResource = (initViewSubLayout())
        val vsView = vsComSimpleDialogContent.inflate()
        val p = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        p.topToBottom = R.id.tvComSimpleDialogTheme
        p.bottomToTop = R.id.btnComSimpleDialogOk
        p.leftToLeft = (vsView.parent as View).id
        p.rightToRight = (vsView.parent as View).id
        p.width = (vsView.parent as View).measuredWidth
        vsView.layoutParams = p
        (btnComSimpleDialogOk.layoutParams as ConstraintLayout.LayoutParams).topToBottom = vsView.id
        (btnComSimpleDialogCancel.layoutParams as ConstraintLayout.LayoutParams).topToBottom = vsView.id
        initText(OK_STR_ID, btnComSimpleDialogOk, getComSimpleDialogOkStrId())
        initText(CANCEL_STR_ID, btnComSimpleDialogCancel, getComSimpleDialogCancelStrId())
        initText(THEME_STR_ID, tvComSimpleDialogTheme, getComSimpleDialogThemeStrId())
        initVSView(vsView)
    }

    private fun initText(bundleKey: String, textView: TextView, defaultId: Int) {
        var okStrId: Int = arguments?.getInt(bundleKey,-1) ?: defaultId
        if (okStrId == -1) {
            okStrId = defaultId
        }
        WkLog.i("okStrId: $okStrId  ")
        textView.setText(okStrId)
    }

    open fun getComSimpleDialogOkStrId() = android.R.string.ok
    open fun getComSimpleDialogCancelStrId() = android.R.string.cancel
    open fun getComSimpleDialogThemeStrId() = R.string.common_str_title

    companion object {
        const val OK_STR_ID = "okStrId"
        const val CANCEL_STR_ID = "cancelStrId"
        const val THEME_STR_ID = "ThemeStrId"
    }
}
