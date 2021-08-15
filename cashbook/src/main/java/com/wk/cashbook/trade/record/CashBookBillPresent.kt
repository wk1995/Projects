package com.wk.cashbook.trade.record

import com.wk.cashbook.trade.data.ITradeRecord
import com.wk.cashbook.trade.data.TradeRecode
import com.wk.cashbook.trade.record.CashBookBillListActivity
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.time.date.DateTime
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/02/20
 * desc         :
 */


class CashBookBillPresent(private val mCashBookBillListActivity: CashBookBillListActivity) {

    /**
     * 获取当前日期的年，月份
     * */
    fun getYearAndMonth():Pair<Int,Int>{
        val currentTime=System.currentTimeMillis()
        val can=Calendar.getInstance()
        can.timeInMillis=currentTime
        val year=can.get(Calendar.YEAR)
        val month=can.get(Calendar.MONTH)+1
        WkLog.d("year: $year  month: $month")
        return Pair(year,month)
    }

    /**
     * 处理数据
     * 1.汇总每日收入、支出
     * 2.汇总收入、支出
     * */
    fun processData(tradeRecords:List<TradeRecode>){
        //收入
        var income=0.0
        //支出
        var pay=0.0
        //内部转账
        var internalTransfer=0.0
        tradeRecords.forEach(
//            if(tradeRecords)

        )
        mCashBookBillListActivity.replaceTradeRecodes(tradeRecords)
    }

    fun getData(time:Long){
        val startTime= DateTime.getMonthStart(time)
        val endTime= DateTime.getMonthEnd(time)
        WkLog.i("initData startTime: ${DateTime.getDateString(startTime)}, endTime: ${DateTime.getDateString(endTime)}")
        Observable.create(Observable.OnSubscribe<List<TradeRecode>> { t ->
            t?.onNext(TradeRecode.getTradeRecodes("${TradeRecode.TRADE_TIME}>? and ${TradeRecode.TRADE_TIME}<?",
                    startTime.toString(),endTime.toString()))
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    WkLog.d("交易记录： $it")
                    processData(it)
                }
    }
}