package com.wk.projects.schedules.data.all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.schedules.R
import com.wk.projects.schedules.communication.constant.SchedulesBundleKey
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.info.ScheduleItemInfoActivity

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/12/10
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class AllDataAdapter(private val context: Context)
    : BaseQuickAdapter<ScheduleItem, BaseViewHolder>(R.layout.schedules_all_data_item) {

    override fun convert(helper: BaseViewHolder?, item: ScheduleItem?) {
        item?.run {
            helper?.setText(R.id.tvScheduleName, itemName)
                    ?.setText(R.id.tvScheduleStartTime, startTime.toString())
                    ?.setText(R.id.tvScheduleEndTime, endTime.toString())

            val rootView = helper?.itemView ?: return
            rootView.setOnClickListener {
                val intent = Intent(context, ScheduleItemInfoActivity::class.java)
                val bundle= Bundle(1)
                bundle.putLong(SchedulesBundleKey.SCHEDULE_ITEM_ID,baseObjId)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }

        }
    }
}