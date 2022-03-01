package com.wk.test.di.hilt

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.wk.test.BaseTestActivity
import com.wk.test.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DiHiltActivity: BaseTestActivity() {
    private lateinit var tvDiHilt: TextView

    @Inject
    lateinit var analytics: AnalyticsAdapter
    override fun initLayout()= R.layout.test_di_hilt_activity

    override fun initView() {
        findViewById<Button>(R.id.btnDiHilt).setOnClickListener(this)
        tvDiHilt=findViewById(R.id.tvDiHilt)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btnDiHilt->{
                analytics.analyticsMethods()
            }
        }

    }
}