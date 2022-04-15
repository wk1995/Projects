package com.wk.projects.schedules.collapse

import com.wk.projects.common.constant.WkStringConstants
import com.wk.projects.common.ui.recycler.ICastStringRvItem
import com.wk.projects.common.ui.recycler.OptimizationRvItem
import com.wk.projects.common.ui.recycler.SimpleStringListAdapter
import java.io.File

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/11/11
 * desc         :
 */


class CollapseListAdapter(mData: MutableList<File> = ArrayList(),
                          mOptimizationRvItem: OptimizationRvItem? = null,
                          mCastStringRvItem: ICastStringRvItem<File>? = null,
                          defaultString: String = WkStringConstants.STR_EMPTY) :
        SimpleStringListAdapter<File>(mData, mOptimizationRvItem, mCastStringRvItem, defaultString)