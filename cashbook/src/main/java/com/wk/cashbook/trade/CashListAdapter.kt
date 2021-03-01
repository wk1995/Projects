package com.wk.cashbook.trade

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wk.cashbook.R
import com.wk.projects.common.time.date.DayUtil
import com.wk.projects.common.time.date.week.WeekUtil
import java.util.*

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/24
 * desc         :
 */


class CashListAdapter(private var mTradeRecords: List<ITradeRecord>) : RecyclerView.Adapter<BaseCashItemVH>() {

    companion object {
        private const val TYPE_TOTAL_ITEM = 0
        private const val TYPE_LIST_ITEM = 1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCashItemVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (TYPE_TOTAL_ITEM == viewType) {
            val rootView = layoutInflater.inflate(R.layout.cashbook_bill_total_item, parent, false)
            val tvBillTotalDay = rootView.findViewById<TextView>(R.id.tvBillTotalDay)
            val tvTradeTotalWeek = rootView.findViewById<TextView>(R.id.tvTradeTotalWeek)
            val tvAllIncomeValue = rootView.findViewById<TextView>(R.id.tvAllIncomeValue)
            val tvAllPayValue = rootView.findViewById<TextView>(R.id.tvAllPayValue)
            return CashTotalItemVH(rootView, tvBillTotalDay, tvTradeTotalWeek, tvAllIncomeValue, tvAllPayValue)
        }

        if (TYPE_LIST_ITEM == viewType) {
            val rootView = layoutInflater.inflate(R.layout.cashbook_bill_list_item, parent, false)
            val ivTradeType = rootView.findViewById<ImageView>(R.id.ivTradeType)
            val tvTradeNote = rootView.findViewById<TextView>(R.id.tvTradeNote)
            val tvTradeAmount = rootView.findViewById<TextView>(R.id.tvTradeAmount)
            return CashListItemVH(rootView, ivTradeType, tvTradeNote, tvTradeAmount)
        }
        throw IllegalAccessException("viewType: $viewType is illegal")
    }

    override fun onBindViewHolder(holder: BaseCashItemVH, position: Int) {
        val tradeRecord = mTradeRecords[position]
        when (holder) {
            is CashTotalItemVH -> {
                if (tradeRecord is TradeRecordTotal) {
                    val date = Date(tradeRecord.date)
                    val allPay = tradeRecord.allPayAmount
                    val allIncome = tradeRecord.allIncomeAmount
                    val week=WeekUtil.getWeek(date).name
                    val dayOfMonth=DayUtil.getDayOfMonth(date)
                    holder.apply {
                        tvAllIncomeValue.text = allIncome.toString()
                        tvAllPayValue.text = allPay.toString()
                        tvTradeTotalWeek.text=week
                        tvBillTotalDay.text=dayOfMonth.toString()
                    }

                }
            }
            is CashListItemVH -> {
                if (tradeRecord is TradeRecordBean) {
                    val type=tradeRecord.type
                    val note=tradeRecord.note
                    val amount=tradeRecord.amount
                    holder.apply {
                        tvTradeAmount.text=amount.toString()
                        tvTradeNote.text=note
                    }
                }
            }
        }
    }

    override fun getItemCount() = mTradeRecords.size

    override fun getItemViewType(position: Int): Int {
        val tradeRecord = mTradeRecords[position]
        return if (tradeRecord is TradeRecordBean) {
            TYPE_LIST_ITEM
        } else {
            TYPE_TOTAL_ITEM
        }
    }

    fun replaceList(tradeRecords:List<ITradeRecord>){
        mTradeRecords=tradeRecords
    }
}