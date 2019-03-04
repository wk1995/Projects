package com.wk.projects.schedules.ui.time

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.wk.projects.schedules.R
import com.wk.projects.schedules.data.ScheduleItem
import org.litepal.LitePal
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/27
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
object TimePickerCreator {

    fun create(context: Context?, listener: OnTimeSelectListener) {
        //时间选择器
        val pvTime = TimePickerBuilder(context, listener)
                .setType(booleanArrayOf(true, true, true, true, true, true))
                .setCancelText(context?.getString(android.R.string.cancel))//取消按钮文字
                .setSubmitText(context?.getString(android.R.string.ok))//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText(context?.getString(R.string.schedules_check_item_end_time))//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build()
        val calender = Calendar.getInstance()
        pvTime.setDate(calender)//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show()
    }
}