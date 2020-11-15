package com.wk.test

import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.wk.test.aidl.AIDLActivity
import com.wk.test.bitmap.TestBitmapActivity
import com.wk.test.touch.TestMotionEventActivity
import kotlinx.android.synthetic.main.test_main_activity.*

class TestMainActivity : BaseTestActivity(), TestListAdapter.ITestItemClickListener {
    companion object {
        const val TOUCH_EVENT = "事件分发"
        const val AIDL = "AIDL"
        const val HANDLER = "HANDLER"
        const val BITMAP="BITMAP"
    }

    private val list by lazy {
        listOf(TOUCH_EVENT, AIDL,HANDLER,BITMAP)
    }

    private val map by lazy {
        mapOf(
                Pair(TOUCH_EVENT, TestMotionEventActivity::class.java),
                Pair(AIDL, AIDLActivity::class.java),
                Pair(HANDLER, TestMotionEventActivity::class.java),
                Pair(BITMAP, TestBitmapActivity::class.java)
        )
    }


    override fun initLayout()=R.layout.test_main_activity

    override fun initView() {
        rvTestList.layoutManager = LinearLayoutManager(this)
        rvTestList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val testListAdapter = TestListAdapter(list)
        testListAdapter.testItemClickListener = this
        rvTestList.adapter = testListAdapter
    }

    override fun onTestItemClick(position: Int) {
        startActivity(Intent(this, map[list[position]]))
    }
}
