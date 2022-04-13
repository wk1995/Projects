package com.wk.test

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.projects.common.ui.recycler.IRvClickListener
import com.wk.test.aidl.AIDLActivity
import com.wk.test.bitmap.TestBitmapActivity
import com.wk.test.coroutines.CoroutinesMainActivity
import com.wk.test.di.DiMainActivity
import com.wk.test.net.TestNetActivity
import com.wk.test.recycle.RecycleViewMainActivity
import com.wk.test.touch.TestMotionEventActivity
import kotlinx.android.synthetic.main.test_main_activity.*

class TestMainActivity : BaseTestActivity(), IRvClickListener {
    companion object {
        const val TOUCH_EVENT = "事件分发"
        const val AIDL = "AIDL"
        const val HANDLER = "HANDLER"
        const val BITMAP="图片"
        const val NET="网络"
        const val RECYCLE_VIEW="recycleView"
        const val DI="依赖项注入"
        const val COROUTINES="协程"
    }

    private val map by lazy {
        mapOf(
                Pair(TOUCH_EVENT, TestMotionEventActivity::class.java),
                Pair(AIDL, AIDLActivity::class.java),
                Pair(HANDLER, TestMotionEventActivity::class.java),
                Pair(BITMAP, TestBitmapActivity::class.java),
                Pair(NET, TestNetActivity::class.java),
                Pair(RECYCLE_VIEW, RecycleViewMainActivity::class.java),
                Pair(DI, DiMainActivity::class.java),
                Pair(COROUTINES, CoroutinesMainActivity::class.java)
        )
    }

    private val list by lazy {
        val list=ArrayList<String>()
        map.forEach {
            list.add(it.key)
        }
        list
    }

    override fun initLayout()=R.layout.test_main_activity

    override fun initView() {
        rvTestList.layoutManager = LinearLayoutManager(this)
        rvTestList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val testListAdapter = TestListAdapter(list)
        testListAdapter.mIRvClickListener
        rvTestList.adapter = testListAdapter
        Thread().interrupt()
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>?, view: View?, position: Int) {
        startActivity(Intent(this, map[list[position]]))
    }
}
