package com.wk.cashbook.trade.record

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wk.cashbook.R
import com.wk.cashbook.databinding.CashbookBillListActivityBinding
import com.wk.cashbook.trade.data.ITradeRecord
import com.wk.cashbook.trade.data.TradeCategory
import com.wk.cashbook.trade.data.TradeRecode
import com.wk.cashbook.trade.info.TradeRecordInfoActivity
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.constant.WkStringConstants.STR_INT_ZERO
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.resource.WkContextCompat
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class CashBookBillListActivity : BaseProjectsActivity(), TabLayout.OnTabSelectedListener {

    companion object {
        private const val LAYOUT_VIEWPAGER_DETAILED = 0
        private const val LAYOUT_VIEWPAGER_CATEGORY = 1
        private const val LAYOUT_VIEWPAGER_ACCOUNT = 2
    }

    class CardViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView)

    private val mBind by lazy {
        CashbookBillListActivityBinding.inflate(layoutInflater)
    }
    private val tabTitles by lazy {
        arrayOf(R.string.common_str_zh_detailed, R.string.common_str_zh_category, R.string.common_str_zh_account)
    }
    private val cashListAdapter by lazy {
        CashListAdapter(ArrayList())
    }

    /**时间*/
    private lateinit var llDate: ConstraintLayout

    /**年*/
    private lateinit var tvDateYear: TextView

    /**月*/
    private lateinit var tvDateMonth: TextView

    /**支出*/
    private lateinit var tvAllPay: TextView

    /**收入*/
    private lateinit var tvIncome: TextView

    /**返回*/
    private lateinit var ivTitleBack: ImageView

    private lateinit var btnAddBill: Button

    private lateinit var vpCashbook: ViewPager2
    private lateinit var tlCashBook: TabLayout

    private lateinit var mCashBookBillPresent: CashBookBillPresent

    override fun beforeSetContentView() {
        super.beforeSetContentView()
        supportActionBar?.hide()
        mCashBookBillPresent = CashBookBillPresent(this)
    }

    override fun initResLayId() = mBind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        initData()
        initView()
        initListener()
        initTime()

    }

    private fun initView() {
        llDate = mBind.llDate
        tvDateYear = mBind.tvDateYear
        tvDateMonth = mBind.tvDateMonth
        tvAllPay = mBind.tvAllPay
        tvAllPay.text = STR_INT_ZERO
        tvIncome = mBind.tvIncome
        tvIncome.text = STR_INT_ZERO
        tlCashBook = mBind.tlCashBook
        btnAddBill = mBind.btnAddBill
        ivTitleBack = mBind.ivTitleBack
        vpCashbook = mBind.vpCashbook
        vpCashbook.adapter = object : RecyclerView.Adapter<CardViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
                val rootView = LayoutInflater.from(parent.context).inflate(
                        getLayoutId(viewType)
                        , parent, false)
                val rvCommon=rootView.findViewById<RecyclerView>(R.id.rvCommon)
                rvCommon?.apply {
                    layoutManager=LinearLayoutManager(parent.context)
                    adapter=cashListAdapter
                    setBackgroundColor(WkContextCompat.getColor(this@CashBookBillListActivity,android.R.color.white))
                }
                return CardViewHolder(rootView)
            }

            override fun getItemCount() = tabTitles.size

            override fun getItemViewType(position: Int): Int {
                return position
            }

            override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
                holder.apply {
                    val tvCommon = rootView.findViewById<TextView>(R.id.tv)
                    tvCommon?.apply {
                        setText(tabTitles[position])
                        textSize = 100f
                        setTextColor(WkContextCompat.getColor(this@CashBookBillListActivity,R.color.colorAccent))
                    }
                }
            }

            private fun getLayoutId(viewType: Int) =
                    if (viewType == LAYOUT_VIEWPAGER_DETAILED) {
                        R.layout.common_only_recycler
                    } else {
                        R.layout.common_default
                    }

        }
        TabLayoutMediator(tlCashBook, vpCashbook) { tab: TabLayout.Tab, position: Int ->
            tab.setText(tabTitles[position])
        }.attach()
    }

    private fun initTime() {
        val yearAndMonth = mCashBookBillPresent.getYearAndMonth()
        tvDateYear.text = yearAndMonth.first.toString()
        tvDateMonth.text = yearAndMonth.second.toString()
    }

    private fun initListener() {
        llDate.setOnClickListener(this)
        ivTitleBack.setOnClickListener(this)
        btnAddBill.setOnClickListener(this)
        tlCashBook.addOnTabSelectedListener(this)
        vpCashbook.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.llDate -> {

            }
            R.id.btnAddBill -> {
                val intene=Intent(this,TradeRecordInfoActivity::class.java)
                startActivityForResult(intene,1)
            }
        }
    }

    private fun initData(){
        Observable.create(Observable.OnSubscribe<List<TradeRecode>> { t ->
            t?.onNext(TradeRecode.getTradeRecodes(null))
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    WkLog.d("交易记录数目： "+it.size)
                    cashListAdapter.replaceList(it)
                }


       /* val calendar=Calendar.getInstance()
        calendar.time= Date()
        val random=Random()
        val tradeRecordBeans=ArrayList<ITradeRecord>()
        for(i in 1..20){
            calendar.set(Calendar.DAY_OF_MONTH,i)
            val num=random.nextInt(5)+1
            for(j in 0 ..num) {
                val dateTime = calendar.timeInMillis
                val amount= random.nextInt(10).toDouble()
                tradeRecordBeans.add(TradeRecordBean(dateTime, i.toString(), amount))
            }
        }
        WkLog.d("tradeRecordBeans size : ${tradeRecordBeans.size}")
        cashListAdapter.replaceList(tradeRecordBeans)*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val tradeRecode=data?.getParcelableExtra<TradeRecode>(TradeRecode.TAG)?:return
        cashListAdapter.addData(tradeRecode)
    }
}