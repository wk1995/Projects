package com.wk.test.coroutines

import com.wk.test.BaseListActivity
import com.wk.test.coroutines.flow.CoroutinesFlowActivity
import com.wk.test.coroutines.flow.CoroutinesStateFlowActivity
import com.wk.test.coroutines.suspend.CoroutinesSuspendActivity

class CoroutinesMainActivity : BaseListActivity<Unit>() {
    companion object {
        const val SUSPEND = "suspend"
        const val FLOW="flow"
        const val STATE_FLOW="StateFlow"
        const val SHARE_FLOW="ShareFlow"
    }

    override fun getListMap(): Map<String, Unit> {
        return mapOf(
            Pair(SUSPEND, startActivity(CoroutinesSuspendActivity::class.java)),
            Pair(FLOW, startActivity(CoroutinesFlowActivity::class.java)),
            Pair(STATE_FLOW, startActivity(CoroutinesStateFlowActivity::class.java)),
            Pair(SHARE_FLOW, startActivity(CoroutinesStateFlowActivity::class.java))
        )
    }
}