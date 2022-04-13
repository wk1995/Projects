package com.wk.test.touch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.wk.projects.common.constant.WkSuppressConstants.CLICKABLE_VIEW_ACCESSIBILITY
import com.wk.projects.common.helper.MotionEventUtil
import com.wk.projects.common.log.WkLogUtil
import com.wk.test.R

class TestMotionEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_motion_event_activity)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        WkLogUtil.d("touchEvent","TestMotionEventActivity: dispatchTouchEvent ${MotionEventUtil.toString(ev)}")
        return super.dispatchTouchEvent(ev)
    }

    @Suppress(CLICKABLE_VIEW_ACCESSIBILITY)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        WkLogUtil.d("touchEvent","TestMotionEventActivity: onTouchEvent ${MotionEventUtil.toString(event)}")
        return super.onTouchEvent(event)
    }
}
