package com.wk.cashbook.trade.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.MainThread
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.cashbook.R
import com.wk.cashbook.databinding.CashbookTradeRecordInfoActivityBinding
import com.wk.cashbook.trade.data.TradeCategory
import com.wk.projects.common.BaseProjectsActivity

class TradeRecordInfoActivity : BaseProjectsActivity(), TradeInfoRootCategoryAdapter.ITradeInfoCategoryListener {

    private val mTradeRecordInfoPresent by lazy {
        TradeRecordInfoPresent(this)
    }

    /**当前的根类别*/
    private var currentRootCategory: TradeCategory? = null
        set(value) {
            field = value
            mTradeRecordInfoPresent.initCategoryAsync(field)
        }

    private val mBind by lazy {
        CashbookTradeRecordInfoActivityBinding.inflate(layoutInflater)
    }
    private val inflater by lazy {
        LayoutInflater.from(this)
    }

    /**
     * 支出，收入，内部转账
     * */
    private val rvTradeInfoRootCategory by lazy {
        mBind.rvTradeInfoRootCategory
    }

    private val rvTradeInfoCategory by lazy {
        mBind.rvTradeInfoCategory
    }

    private val mTradeInfoRootCategoryAdapter by lazy {
        TradeInfoRootCategoryAdapter(mTradeRecordInfoPresent = mTradeRecordInfoPresent,
                mTradeInfoCategoryListener=this)
    }
    private val mTradeInfoCategoryAdapter by lazy {
        TradeInfoRootCategoryAdapter(mTradeRecordInfoPresent = mTradeRecordInfoPresent,
                mTradeInfoCategoryListener=this)
    }
    private val rootCategoryLayoutManage by lazy {
        LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private val categoryLayoutManger by lazy {
        GridLayoutManager(this, 4)
    }

    override fun initResLayId() = mBind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        initRootCategoryRv()
        initCategoryRv()
        mTradeRecordInfoPresent.initRootCategoryAsync()
    }

    private fun initRootCategoryRv() {
        rvTradeInfoRootCategory.layoutManager = rootCategoryLayoutManage
        rvTradeInfoRootCategory.adapter = mTradeInfoRootCategoryAdapter
    }

    private fun initCategoryRv() {
        val footView = inflater.inflate(R.layout.common_only_text, null)
        mTradeInfoCategoryAdapter.addFootView(footView)
        rvTradeInfoCategory.layoutManager = categoryLayoutManger
        rvTradeInfoCategory.adapter = mTradeInfoCategoryAdapter

    }

    @MainThread
    fun setRootCategory(rootCategories: List<TradeCategory>) {
        currentRootCategory = rootCategories[0]
        mTradeInfoRootCategoryAdapter.replaceData(rootCategories)
    }

    fun setCategories(categories: List<TradeCategory>) {
        mTradeInfoCategoryAdapter.replaceData(categories)
    }

    fun addCategory(category:TradeCategory){
        mTradeInfoCategoryAdapter.addCategory(category)
    }

    override fun itemClick(tradeInfoRootCategoryAdapter: TradeInfoRootCategoryAdapter, view: View, position: Int) {
        if (tradeInfoRootCategoryAdapter == mTradeInfoCategoryAdapter && !tradeInfoRootCategoryAdapter.isItem(position)) {
            mTradeRecordInfoPresent.showAddCategoryDialog()
        }

    }
}