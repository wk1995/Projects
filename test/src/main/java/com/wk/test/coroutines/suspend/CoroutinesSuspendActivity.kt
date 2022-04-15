package com.wk.test.coroutines.suspend

import android.os.Bundle
import com.wk.projects.common.BaseProjectsActivity
import com.wk.test.R
import com.wk.test.TestLogUtil

/**
 * suspend用于暂停执行当前协程，并保存所有局部变量。
 * 如需调用suspend函数，只能从其他suspend函数进行调用，或通过使用协程构建器（例如 launch）来启动新的协程。
 *
https://www.bennyhuo.com/book/kotlin-coroutines/06-suspend.html#_1-%E5%85%88%E7%9C%8B%E7%9C%8B-delay
 *
 * */
class CoroutinesSuspendActivity : BaseProjectsActivity() {
    override fun initResLayId()= R.layout.test_coroutines_suspend_layout_activity

    override fun bindView(
        savedInstanceState: Bundle?,
        mBaseProjectsActivity: BaseProjectsActivity
    ) {

    }

}