package com.wk.test.coroutines

import com.wk.test.BaseListActivity
import com.wk.test.coroutines.flow.CoroutinesFlowActivity
import com.wk.test.coroutines.suspend.CoroutinesSuspendActivity

class CoroutinesMainActivity : BaseListActivity<Unit>() {
    companion object {
        const val SUSPEND = "suspend"
        const val FLOW="flow"
        const val FLOW_AND_STATE_FLOW="flow And StateFlow"
        const val FLOW_AND_SHARE_FLOW="flow And ShareFlow"
        const val STATE_FLOW_AND_SHARE_FLOW="StateFlow And ShareFlow"
    }

    override fun getListMap(): Map<String, Unit> {
        return mapOf(
            Pair(SUSPEND, startActivity(CoroutinesSuspendActivity::class.java)),
            Pair(FLOW, startActivity(CoroutinesFlowActivity::class.java)),
            Pair(FLOW_AND_SHARE_FLOW, startActivity(CoroutinesMainActivity::class.java)),
            Pair(STATE_FLOW_AND_SHARE_FLOW, startActivity(CoroutinesMainActivity::class.java))
        )
    }
}