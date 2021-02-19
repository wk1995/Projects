package com.wk.projects.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.wk.projects.common.R

/**
 * author : wk <br></br>
 * e-mail : 1226426603@qq.com<br></br>
 * time   : 2020/6/7<br></br>
 * desc   : 统一的actionBar  <br></br>
 * GitHub : https://github.com/wk1995 <br></br>
 * CSDN   : http://blog.csdn.net/qq_33882671 <br></br>
 */
class WkCommonActionBar : ConstraintLayout {

    private val mLayoutInflater by lazy { LayoutInflater.from(context) }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        initLayout(R.layout.common_action_bar_default)
    }

    private fun initLayout(layoutResId:Int){
        mLayoutInflater.inflate(layoutResId,this,true)
    }

}
