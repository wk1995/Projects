package com.wk.projects.activities.data.`class`

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.activities.R
import com.wk.projects.activities.data.WkActivity

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 显示类别
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