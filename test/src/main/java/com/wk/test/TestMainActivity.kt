package com.wk.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.wk.test.touch.TestMotionEventActivity
import kotlinx.android.synthetic.main.test_main_activity.*

class TestMainActivity : AppCompatActivity(), TestListAdapter.ITestItemClickListener {
    companion object {
        const val TOUCH_EVENT = "事件分发"
        const val AIDL = "AIDL"
    }

    private val list by lazy {
        listOf(TOUCH_EVENT, AIDL)
    }

    private val map by lazy {
        mapOf(
                Pair(TOUCH_EVENT, TestMotionEventActivity::class.java),
                Pair(AIDL, TestMotionEventActivity::class.java)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main_activity)
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
