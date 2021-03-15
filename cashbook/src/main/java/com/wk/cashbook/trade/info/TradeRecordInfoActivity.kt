package com.wk.cashbook.trade.info

import android.os.Bundle
import com.wk.cashbook.databinding.CashbookTradeRecordInfoActivityBinding
import com.wk.projects.common.BaseProjectsActivity

class TradeRecordInfoActivity : BaseProjectsActivity() {

    private val mBind by lazy{
        CashbookTradeRecordInfoActivityBinding.inflate(layoutInflater)
    }

    override fun initResLayId()=mBind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {

    }
}