package com.wk.test.coroutines.flow

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wk.projects.common.BaseProjectsActivity
import com.wk.test.R
import com.wk.test.TestLogUtil
import kotlinx.coroutines.flow.collect

class CoroutinesStateFlowActivity : BaseProjectsActivity() {

    private lateinit var btnStateFlowEmit: Button
    private lateinit var btnStateFlowShow: Button

    private val mViewModel: CoroutinesStateFlowViewModel by lazy {
        ViewModelProvider(this).get(CoroutinesStateFlowViewModel::class.java)
    }

    override fun initResLayId() = R.layout.test_coroutines_state_flow_activity


    private var i = 0
    override fun bindView(
        savedInstanceState: Bundle?,
        mBaseProjectsActivity: BaseProjectsActivity
    ) {
        btnStateFlowEmit = findViewById(R.id.btnStateFlowEmit)
        btnStateFlowShow = findViewById(R.id.btnStateFlowShow)
        btnStateFlowShow.text = i.toString()
        btnStateFlowEmit.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            mViewModel._sample.collect {
                TestLogUtil.log("_sample collect  start")
                ++i
                btnStateFlowShow.text = i.toString()
                TestLogUtil.log("_sample collect  end")
            }
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.sample.collect {
                TestLogUtil.log("sample collect  start")
                ++i
                btnStateFlowShow.text = i.toString()
                TestLogUtil.log("sample collect  end")
            }
        }

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.btnStateFlowEmit -> {
                mViewModel.way()
            }
        }

    }
}