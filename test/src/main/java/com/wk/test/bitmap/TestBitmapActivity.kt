package com.wk.test.bitmap

import android.graphics.BitmapFactory
import android.view.View
//import com.bumptech.glide.Glide
import com.wk.projects.common.helper.WkBitmapUtil
import com.wk.projects.common.log.WkLogUtil
import com.wk.test.BaseTestActivity
import com.wk.test.R
import kotlinx.android.synthetic.main.test_bitmap_layout_activity_main.*


class TestBitmapActivity : BaseTestActivity(), View.OnClickListener {
    private val bitmap by lazy {
        listOf(rbTestBitmapOrigin, rbTestBitmapGlide, rbTestBitmapDeal)
    }

    override fun initLayout() = R.layout.test_bitmap_layout_activity_main

    override fun initView() {
        btnTestBitmapSet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnTestBitmapSet -> {
                when {
                    rbTestBitmapOrigin.isChecked -> {
                        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_bitmap_test)
//                tvTestShowBitmap.setImageBitmap(bitmap)
                    }
                    rbTestBitmapGlide.isChecked -> {
//                        Glide.with(this).load(R.drawable.test_bitmap_test).into(tvTestShowBitmap)
                    }
                    rbTestBitmapDeal.isChecked -> {
                        val options = BitmapFactory.Options()
                        options.inJustDecodeBounds = true
                        BitmapFactory.decodeResource(resources, R.drawable.test_bitmap_test, options)
                        val sampleSize=WkBitmapUtil.calculateInSampleSize(options,200,200)
                        WkLogUtil.d("wk", "sampleSize: $sampleSize")
                        options.inSampleSize=sampleSize
                        options.inJustDecodeBounds = false
                        val bitmap=BitmapFactory.decodeResource(resources, R.drawable.test_bitmap_test, options)
                        WkLogUtil.d("wk", WkBitmapUtil.getBitmapSize(bitmap).toString())
                        tvTestShowBitmap.setImageBitmap(bitmap)
                    }

                }


            }
        }
    }
}
