package com.wk.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/9/28<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * */
abstract class BaseTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        initView()
        initData()
    }

    abstract fun initLayout(): Int

    abstract fun initView()

    protected open fun initData(){

    }
}