package com.wk.projects.schedules.data.`class`

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.schedules.R
import com.wk.projects.schedules.data.WkActivity

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
class CategoryAdapter : BaseQuickAdapter<WkActivity, BaseViewHolder>(R.layout.common_only_text) {

    override fun convert(helper: BaseViewHolder?, item: WkActivity?) {
        item?.run {
            helper?.setText(R.id.tvCommon, itemName)
                    ?.addOnClickListener(R.id.tvCommon)

        }
    }
}