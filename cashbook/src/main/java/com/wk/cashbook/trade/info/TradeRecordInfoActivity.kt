package com.wk.cashbook.trade.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextClock
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wk.cashbook.R
import com.wk.cashbook.databinding.CashbookTradeRecordInfoActivityBinding
import com.wk.cashbook.trade.data.TradeCategory
import com.wk.cashbook.trade.data.TradeRecode
import com.wk.cashbook.trade.record.CashBookBillListActivity
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.ui.WkToast

class TradeRecordInfoActivity : BaseProjectsActivity(), TradeInfoCategoryAdapter.ITradeInfoCategoryListener {

    private val mTradeRecordInfoPresent by lazy {
        TradeRecordInfoPresent(this)
    }
    /**保存按钮*/
    private lateinit var btTradeInfoSave:Button

    /**金额*/
    private lateinit var tvTradeInfoAmount:EditText
    /**备注*/
    private lateinit var etTradeInfoNote:EditText

    /**时间*/
    private lateinit var tvTradeInfoTime:TextView
    /**标签*/
    private lateinit var tvTradeInfoFlag:TextView

    /**账户*/
    private lateinit var tvTradeInfoAccount:TextView

    /**
     * 支出，收入，内部转账
     * */
    private val rvTradeInfoRootCategory by lazy {
        mBind.rvTradeInfoRootCategory
    }

    private val rvTradeInfoCategory by lazy {
        mBind.rvTradeInfoCategory
    }



    private val mBind by lazy {
        CashbookTradeRecordInfoActivityBinding.inflate(layoutInflater)
    }
    private val inflater by lazy {
        LayoutInflater.from(this)
    }



    private val mTradeInfoRootCategoryAdapter by lazy {
        TradeInfoRootCategoryAdapter(mTradeRecordInfoPresent = mTradeRecordInfoPresent,
                mTradeInfoCategoryListener = this)
    }
    private val mTradeInfoCategoryAdapter by lazy {
        TradeInfoCategoryAdapter(mTradeRecordInfoPresent = mTradeRecordInfoPresent,
                mTradeInfoCategoryListener = this)
    }
    private val rootCategoryLayoutManage by lazy {
        LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private val categoryLayoutManger by lazy {
        GridLayoutManager(this, 4)
    }

    override fun initResLayId() = mBind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        initView()
        initRootCategoryRv()
        initCategoryRv()
        mTradeRecordInfoPresent.initRootCategoryAsync()
        btTradeInfoSave=mBind.btTradeInfoSave
        btTradeInfoSave.setOnClickListener(this)
        val tradeRecode=intent.getParcelableExtra<TradeRecode>(TradeRecode.TAG)

    }
    private fun initView(){
        btTradeInfoSave=mBind.btTradeInfoSave
        tvTradeInfoAmount=mBind.tvTradeInfoAmount
        etTradeInfoNote=mBind.etTradeInfoNote
        tvTradeInfoTime=mBind.tvTradeInfoTime
        tvTradeInfoFlag=mBind.tvTradeInfoFlag
        tvTradeInfoAccount=mBind.tvTradeInfoAccount
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
        mTradeInfoRootCategoryAdapter.replaceData(rootCategories)
        mTradeRecordInfoPresent.currentRootCategory = rootCategories[0]
    }

    fun setCategories(categories: List<TradeCategory>) {
        mTradeInfoCategoryAdapter.replaceData(categories)
    }

    fun addCategory(category: TradeCategory) {
        mTradeInfoCategoryAdapter.addCategory(category)
    }

    override fun itemClick(tradeInfoCategoryAdapter: TradeInfoCategoryAdapter, view: View, position: Int) {
        //类别
        if (tradeInfoCategoryAdapter == mTradeInfoCategoryAdapter) {
            //添加类别
            if (!tradeInfoCategoryAdapter.isItem(position)) {
                mTradeRecordInfoPresent.showAddCategoryDialog()
            } else {
                //选中类别
                mTradeInfoCategoryAdapter.selectPosition(position)
            }
            return
        }

        if (tradeInfoCategoryAdapter == mTradeInfoRootCategoryAdapter) {
            //选中类别
            mTradeInfoRootCategoryAdapter.selectPosition(position)
            mTradeRecordInfoPresent.currentRootCategory = mTradeInfoRootCategoryAdapter.getItem(position)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btTradeInfoSave->{
                mTradeRecordInfoPresent.saveTradeRecode()
            }
        }
    }

    fun saveResult(tradeRecode: TradeRecode?){
        if(null==tradeRecode){
            WkToast.showToast("保存失败")
            return
        }
        val intent=Intent()
        intent.putExtra(TradeRecode.TAG,tradeRecode)
        setResult(2,intent)
        finish()
    }
}