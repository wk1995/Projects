package com.wk.projects.schedules.data.add

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.schedules.R

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
class ScheduleItemNameListAdapter<T>(private val adapterItemCastString: AdapterItemCastString<T>?)
    : BaseQuickAdapter<T, BaseViewHolder>(R.layout.common_only_text) {


    interface AdapterItemCastString<T>{
        fun castString(t:T?):String?
    }


    private val originalList by lazy { ArrayList<T>() }
    private val filterList by lazy { ArrayList<T>() }

    override fun convert(helper: BaseViewHolder?, item: T?) {
        getItemString(item)?.run {
            helper?.setText(R.id.tvCommon, this)
                    ?.addOnClickListener(R.id.tvCommon)
        }
    }

    private fun getItemString(item: T?)=adapterItemCastString?.castString(item)

    fun initData(itemList: List<T>) {
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
            if ((getItemString(it)?.contains(containChar, true))==true)
                filterList.add(it)
        }
        setNewData(filterList)
    }
}