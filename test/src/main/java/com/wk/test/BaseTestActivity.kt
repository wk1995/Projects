package com.wk.test

import android.os.Bundle
import com.wk.projects.common.BaseProjectsActivity

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/9/28<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
abstract class BaseTestActivity : BaseProjectsActivity() {

    override fun initResLayId() = initLayout()

    override fun bindView(
        savedInstanceState: Bundle?,
        mBaseProjectsActivity: BaseProjectsActivity
    ) {
        initView()
        initData()
    }

    abstract fun initLayout(): Int

    abstract fun initView()

    protected open fun initData() {

    }
}