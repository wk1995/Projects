package com.wk.projects.common

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewStub
import android.widget.Button
import android.widget.TextView
import butterknife.BindView

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

    @BindView(R2.id.vsComSimpleDialogContent)
    protected lateinit var vsComSimpleDialogContent: ViewStub
    @BindView(R2.id.btnComSimpleDialogOk)
    protected lateinit var btnComSimpleDialogOk: Button
    @BindView(R2.id.tvComSimpleDialogTheme)
    protected lateinit var tvComSimpleDialogTheme: TextView
    @BindView(R2.id.btnComSimpleDialogCancel)
    protected lateinit var btnComSimpleDialogCancel: Button

    final override fun initResLayId() = R.layout.common_dialog_fragment_base_simple

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        initView()
        initListener()
    }

    override fun onClick(v: View?) {
        disMiss()
    }

    protected fun disMiss() {
        if (dialog != null && dialog.isShowing)
            dismiss()
    }

    fun show(manager: FragmentManager?) {
        super.show(manager, this::class.java.simpleName)
    }
    fun show(baseProjectsActivity: BaseProjectsActivity){
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
        initVSView(vsView)
    }
}
