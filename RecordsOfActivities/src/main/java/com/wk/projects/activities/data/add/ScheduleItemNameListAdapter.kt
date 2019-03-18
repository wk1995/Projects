package com.wk.projects.activities.data.add

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.activities.R

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/12/12
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
class ScheduleItemNameListAdapter
    : BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_only_text) {

    private val originalList by lazy { ArrayList<String>() }
    private val filterList by lazy { ArrayList<String>() }

    override fun convert(helper: BaseViewHolder?, item: String?) {
        item?.run {
            helper?.setText(R.id.tvCommon, this)
                    ?.addOnClickListener(R.id.tvCommon)
        }
    }

    fun initData(itemList: List<String>) {
        originalList.clear()
        filterList.clear()
        originalList.addAll(itemList)
        filterList.addAll(itemList)
        setNewData(filterList)
    }

    fun search(containChar: String) {
        if (containChar.trim().isBlank()) {
            setNewData(originalList)
            return
        }
        filterList.clear()
        originalList.forEach {
            if (it.contains(containChar, true))
                filterList.add(it)
        }
        setNewData(filterList)
    }
}