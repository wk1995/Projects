package com.wk.period

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MultiMonthView
import com.wk.period.simple.SimpleMonthView
import com.wk.projects.common.ui.WkToast

/**
 *
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2021/4/25
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * */
class MultiLongMonthView(context: Context?) : MultiMonthView(context) {
    private var mRadius = 0

    private val mSimpleMonthView by lazy{
        SimpleMonthView(context)
    }


    override fun onPreviewHook() {
        mRadius = mItemWidth.coerceAtMost(mItemHeight) / 5 * 2
        mSchemePaint.style = Paint.Style.STROKE
    }

    override fun onDrawSelected(canvas: Canvas, calendar: Calendar?, x: Int, y: Int, hasScheme: Boolean,
                                isSelectedPre: Boolean, isSelectedNext: Boolean): Boolean {
        val cx: Int = x + mItemWidth / 2
        val cy: Int = y + mItemHeight / 2
        if (isSelectedPre) {
            if (isSelectedNext) {
                canvas.drawRect(x.toFloat(), (cy - mRadius).toFloat(), (x + mItemWidth).toFloat(), (cy + mRadius).toFloat(), mSelectedPaint)
            } else { //最后一个，the last
                canvas.drawRect(x.toFloat(), (cy - mRadius).toFloat(), cx.toFloat(), (cy + mRadius).toFloat(), mSelectedPaint)
                canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSelectedPaint)
            }
        } else {
            if (isSelectedNext) {
                canvas.drawRect(cx.toFloat(), (cy - mRadius).toFloat(), (x + mItemWidth).toFloat(), (cy + mRadius).toFloat(), mSelectedPaint)
            }
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSelectedPaint)
            //
        }
        return false
    }

    override fun onDrawScheme(canvas: Canvas, calendar: Calendar?, x: Int, y: Int, isSelected: Boolean) {
        val cx: Int = x + mItemWidth / 2
        val cy: Int = y + mItemHeight / 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSchemePaint)
    }

    override fun onDrawText(canvas: Canvas, calendar: Calendar, x: Int, y: Int, hasScheme: Boolean, isSelected: Boolean) {
        val baselineY: Float = mTextBaseLine + y
        val cx: Int = x + mItemWidth / 2
        val isInRange: Boolean = isInRange(calendar)
        val isEnable: Boolean = !onCalendarIntercept(calendar)
        if (isSelected) {
            canvas.drawText(calendar.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    mSelectTextPaint)
        } else if (hasScheme) {
            canvas.drawText(calendar.day.toString(),
                    cx.toFloat(),
                    baselineY,
                    if (calendar.isCurrentDay) {
                        mCurDayTextPaint
                    } else if (calendar.isCurrentMonth && isInRange && isEnable) {
                        mSchemeTextPaint
                    } else {
                        mOtherMonthTextPaint
                    }
            )
        } else {
            canvas.drawText(calendar.day.toString(), cx.toFloat(), baselineY,
                    if (calendar.isCurrentDay) {
                        mCurDayTextPaint
                    } else if (calendar.isCurrentMonth && isInRange && isEnable) {
                        mCurMonthTextPaint
                    } else {
                        mOtherMonthTextPaint
                    })
        }
    }

    override fun onLongClick(v: View?): Boolean {
        return mSimpleMonthView.onLongClick(v)
    }

}