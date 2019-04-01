package com.wk.projects.records.consumption.data.detail

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.wk.projects.common.BaseFragment
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.ui.widget.time.TimePickerCreator
import com.wk.projects.records.consumption.R
import kotlinx.android.synthetic.main.consumption_fragment_add_detail.*
import java.util.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/3/31
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 添加消费记录
 * </pre>
 */
@Route(path = ARoutePath.AddDetailFragment)
class AddDetailFragment: BaseFragment(),View.OnClickListener, OnTimeSelectListener {

    override fun initResLay()= R.layout.consumption_fragment_add_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvAddDetailTime.text=System.currentTimeMillis().toString()
        initRecyclerView()
        tvAddDetailTime.setOnClickListener(this)
        tvAddDetailNote.setOnClickListener(this)
        tvAddDetailAccount.setOnClickListener(this)
        tvAddDetailType.setOnClickListener(this)
    }
    private fun initRecyclerView(){

    }

    override fun onClick(v: View?) {
        when(v){
            //切换时间
            tvAddDetailTime->{
                TimePickerCreator.create(_mActivity,this)
            }
            //填写备注
            tvAddDetailNote->{

            }
            //选择账户
            tvAddDetailAccount->{}
            //选择类型
            tvAddDetailType->{}
        }
    }

    override fun onTimeSelect(date: Date?, v: View?) {
        tvAddDetailTime.text=date?.time.toString()
    }
}