package com.wk.projects.common.collapse

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.io.File

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/12
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class CollapseRvAdapter(private val list: List<File>)
    : BaseQuickAdapter<File, BaseViewHolder>(android.R.layout.simple_list_item_1,list) {

    override fun convert(helper: BaseViewHolder?, item: File?) {
        val tv=helper?.getView<TextView>(android.R.id.text1)
        tv?.text=item?.name
        tv?.setTextColor(Color.WHITE)
        helper?.addOnClickListener(android.R.id.text1)
    }
}