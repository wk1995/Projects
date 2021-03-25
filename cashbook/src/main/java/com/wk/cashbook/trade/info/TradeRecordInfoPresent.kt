package com.wk.cashbook.trade.info

import com.wk.cashbook.trade.data.TradeCategory
import com.wk.projects.common.SimpleOnlyEtDialog
import com.wk.projects.common.constant.NumberConstants
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.ui.WkToast
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author      :wangkang_shenlong
 * email        :shenlong.wang@tuya.com
 * create date  : 2021/03/16
 * desc         :
 */


class TradeRecordInfoPresent(private val mTradeRecordInfoActivity: TradeRecordInfoActivity)
    : SimpleOnlyEtDialog.SimpleOnlyEtDialogListener {

    private var currentCategory:TradeCategory?=null
    /**
     * 获取最顶的类别
     * 支出，收入，内部转账
     * */

    fun initRootCategoryAsync() {
        Observable.create(Observable.OnSubscribe<List<TradeCategory>> { t ->
            t?.onNext(TradeCategory.getRootCategory())
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    WkLog.d(it.toString())
                    mTradeRecordInfoActivity.setRootCategory(it)
                }
    }


    fun initCategoryAsync(parent: TradeCategory?) {
        Observable.create(Observable.OnSubscribe<List<TradeCategory>> { t ->
            t?.onNext(TradeCategory.getSubCategory(parent))
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    WkLog.d(it.toString())
                    mTradeRecordInfoActivity.setCategories(it)
                }
    }



    fun showAddCategoryDialog() {
        val simpleOnlyEtDialog=SimpleOnlyEtDialog.create(simpleOnlyEtDialogListener=this)
        simpleOnlyEtDialog.show(mTradeRecordInfoActivity)
    }


    private fun saveNewCategory(categoryName:String){
        val newCategory=TradeCategory(categoryName,System.currentTimeMillis(),
                currentCategory?.baseObjId?:NumberConstants.number_long_one_Negative)
        Observable.create(Observable.OnSubscribe<Boolean> { t ->
            t?.onNext(newCategory.save())
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it){
                        mTradeRecordInfoActivity.addCategory(newCategory)
                    }else{
                        WkToast.showToast("失败")
                    }
                }
    }

    override fun ok(textString: String?): Boolean {
        if(textString.isNullOrBlank()){
            WkToast.showToast("不能为空")
            return true
        }
        saveNewCategory(textString)
        return false
    }

    override fun cancel(): Boolean {
        return false
    }
}