package com.wk.projects.schedules.info

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.schedules.R

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class ScheduleInfoAdapter :
        BaseQuickAdapter<Pair<String,String>, BaseViewHolder>(R.layout.common_ll_tv_vertical_tv) {

    override fun convert(helper: BaseViewHolder?, item: Pair<String,String>?) {
        item?.run {
            helper?.setText(R.id.tvTopCommonLlIvVTv, first)
                    ?.setText(R.id.tvBottomCommonLlTvVTv, second)
        }
    }
}