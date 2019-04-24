package com.wk.projects.common.listener

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemSwipeListener
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
abstract class BaseOnItemSwipeListener : OnItemSwipeListener {
    override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
        Timber.i("OnItemSwipeListener  onItemSwiped  pos:  $pos")
    }

    override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
        Timber.i("OnItemSwipeListener  onItemSwipeStart  pos:  $pos")
    }

    override fun onItemSwipeMoving(canvas: Canvas?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, isCurrentlyActive: Boolean) {
        Timber.i("OnItemSwipeListener  onItemSwipeMoving  dX:  $dX  dY:  $dY  isCurrentlyActive:  $isCurrentlyActive")
    }

    override fun clearView(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
        Timber.i("OnItemSwipeListener  clearView  pos:  $pos")
    }
}