package com.wk.test.di.dagger

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.wk.test.BaseTestActivity
import com.wk.test.R

class DiDaggerActivity : BaseTestActivity() {
    private lateinit var tvDiDagger:TextView

    override fun initLayout()= R.layout.test_di_dagger_activity

    override fun initView() {
        findViewById<Button>(R.id.btnDiDagger).setOnClickListener(this)
        tvDiDagger=findViewById(R.id.tvDiDagger)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btnDiDagger->{
            }
        }

    }
}