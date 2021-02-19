package com.wk.projects.schedules.ui.recycler

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView
import com.wk.projects.schedules.data.ScheduleItem
import com.wk.projects.schedules.data.all.AllDataAdapter
import org.litepal.LitePal
import java.lang.Exception

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
class RefreshHelper(private val context: Context,
                    private val rv: androidx.recyclerview.widget.RecyclerView,
                    private val sr: androidx.swiperefreshlayout.widget.SwipeRefreshLayout)
    : androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        sr.isRefreshing = true
        try {
            LitePal.findAllAsync(ScheduleItem::class.java).listen {
                val mAllDataAdapter = AllDataAdapter(context)
                rv.adapter = mAllDataAdapter
                mAllDataAdapter.setNewData(it)
                sr.isRefreshing = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            sr.isRefreshing = false
        }

    }
}