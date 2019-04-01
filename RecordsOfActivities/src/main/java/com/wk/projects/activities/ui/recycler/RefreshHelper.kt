package com.wk.projects.activities.ui.recycler

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.wk.projects.activities.data.ScheduleItem
import com.wk.projects.activities.data.query.AllDataAdapter
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
                    private val rv: RecyclerView,
                    private val sr: SwipeRefreshLayout)
    : SwipeRefreshLayout.OnRefreshListener {

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