package com.wk.projects.common.listener

import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemDragListener
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/23
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
abstract class BaseOnItemDragListener : OnItemDragListener {
    override fun onItemDragMoving(source: RecyclerView.ViewHolder?, from: Int, target: RecyclerView.ViewHolder?, to: Int) {
        Timber.i("OnItemDragListener  onItemDragMoving  from:  $from  to:  $to")
    }

    override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
        Timber.i("OnItemDragListener  onItemDragStart  pos:  $pos")
    }

    override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
        Timber.i("OnItemDragListener  onItemDragEnd  pos:  $pos")
    }
}