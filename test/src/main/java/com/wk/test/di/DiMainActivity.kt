package com.wk.test.di

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.projects.common.ui.recycler.IRvClickListener
import com.wk.test.BaseTestActivity
import com.wk.test.R
import com.wk.test.di.dagger.DiDaggerActivity
import com.wk.test.di.hilt.DiHiltActivity

class DiMainActivity : BaseTestActivity(), IRvClickListener {

    companion object {
        const val DI_TYPE_DAGGER = "dagger"
        const val DI_TYPE_HILT = "hilt"
    }

    private lateinit var rvDiMainList: RecyclerView
    private val mDiListAdapter by lazy {
        val mDiListAdapter = DiListAdapter(mData)
        mDiListAdapter.mIRvClickListener = this
        mDiListAdapter
    }

    private val mData by lazy {
        mutableListOf(
            Pair(DI_TYPE_DAGGER, DiDaggerActivity::class.java),
            Pair(DI_TYPE_HILT, DiHiltActivity::class.java)
        )
    }

    override fun initLayout() = R.layout.test_di_main_activity

    override fun initView() {
        rvDiMainList = findViewById(R.id.rvDiMainList)
        rvDiMainList.layoutManager = LinearLayoutManager(this)
        rvDiMainList.adapter = mDiListAdapter
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>?, view: View?, position: Int) {
        super.onItemClick(adapter, view, position)
        val intent = Intent(this, mDiListAdapter.getItem(position).second)
        startActivity(intent)
    }
}