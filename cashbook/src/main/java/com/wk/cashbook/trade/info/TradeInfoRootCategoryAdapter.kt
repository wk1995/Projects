package com.wk.cashbook.trade.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wk.cashbook.R
import com.wk.cashbook.trade.data.TradeCategory
import com.wk.projects.common.ui.WkToast
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/03/18
 * desc         :
 */


class TradeInfoRootCategoryAdapter(private var categories: MutableList<TradeCategory> = ArrayList(),
                                   private val mTradeRecordInfoPresent: TradeRecordInfoPresent,
                                   private val mTradeInfoCategoryListener: ITradeInfoCategoryListener? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_FOOT = -1
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1

    }

    interface ITradeInfoCategoryListener {
        fun itemClick(tradeInfoRootCategoryAdapter: TradeInfoRootCategoryAdapter, view: View, position: Int)
    }

    private val footViews = HashMap<Int, View>()
    private val headerViews = HashMap<Int, View>()

    class RootCategoryVH(rootView: View, val tvCommon: TextView) : RecyclerView.ViewHolder(rootView)
    class CategoryVH(rootView: View) : RecyclerView.ViewHolder(rootView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeader(position) || isFoot(position)) {
            if (holder is CategoryVH) {
                val tvCommon = holder.itemView.findViewById<TextView>(R.id.tvCommon)
                tvCommon.setBackgroundResource(R.color.colorAccent)
                tvCommon?.text = "+"
                tvCommon.setOnClickListener {
                    mTradeInfoCategoryListener?.itemClick(this, tvCommon, position)
                }
            }
        } else {
            val category = categories[position]
            if (holder is RootCategoryVH) {
                holder.tvCommon.text = category.categoryName
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (isFoot(position) || isHeader(position)) {
            return position
        }
        return VIEW_TYPE_ITEM
    }

    private fun isHeader(position: Int) = position < headerViews.size

    private fun isFoot(position: Int) = position >= itemCount - footViews.size

     fun isItem(position: Int) = !isHeader(position) && !isFoot(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val rootView = LayoutInflater.from(parent.context).inflate(R.layout.common_only_text, parent, false)
            val tvCommon = rootView.findViewById<TextView>(R.id.tvCommon)
            val lp = tvCommon.layoutParams
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT
            tvCommon.layoutParams = lp
            return RootCategoryVH(rootView, tvCommon)
        }
        if (viewType < headerViews.size) {
            return CategoryVH(headerViews[viewType] ?: throw Exception())
        }

        return CategoryVH(footViews[viewType] ?: throw Exception())
    }

    override fun getItemCount() = categories.size + footViews.size + headerViews.size

    fun replaceData(categories: List<TradeCategory>) {
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    fun addCategory(category:TradeCategory){
        this.categories.add(category)
        notifyItemChanged(itemCount-footViews.size-headerViews.size)
    }

    fun addFootView(footView: View) {
        footViews[footViews.size] = footView
    }

    fun addHeaderView(headerView: View) {
        headerViews[footViews.size] = headerView
    }
}