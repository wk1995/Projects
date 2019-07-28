package com.wk.projects.activities.data.add

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.communication.constant.RequestCode
import com.wk.projects.activities.communication.constant.ResultCode
import com.wk.projects.activities.communication.constant.SchedulesBundleKey
import com.wk.projects.activities.data.WkActivity
import com.wk.projects.activities.data.add.adapter.ActivitiesBean
import com.wk.projects.activities.data.add.adapter.CategoryListAdapter
import com.wk.projects.common.ui.dialog.BaseSimpleDialog
import com.wk.projects.common.communication.constant.BundleKey
import com.wk.projects.common.listener.BaseSimpleClickListener
import com.wk.projects.common.ui.notification.ToastUtil
import org.litepal.LitePal
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      coordinateDesc   : 类别的列表
 * </pre>
 */
class CategoryDialog : BaseSimpleDialog(), View.OnClickListener {
    //需要变更父类的WkActivity的id
    private val targetId by lazy { arguments?.getLong(WkActivity.ACTIVITY_ID) }


    companion object {
        fun create(bundle: Bundle? = null): CategoryDialog {
            val mCategoryDialog = CategoryDialog()
            mCategoryDialog.arguments = bundle
            return mCategoryDialog
        }
    }

    override fun initViewSubLayout() = R.layout.common_only_recycler

    private val cateGoryList by lazy { ArrayList<ActivitiesBean>() }
    private val mCategoryListAdapter by lazy { CategoryListAdapter() }


    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        btnComSimpleDialogOk.visibility = View.GONE
        btnComSimpleDialogCancel.visibility = View.GONE
    }

    override fun initVSView(vsView: View) {
        super.initVSView(vsView)
        val rvCommon = vsView.findViewById<RecyclerView>(R.id.rvCommon)
        val rvCommonLp = rvCommon.layoutParams
        rvCommonLp.height = mActivity.resources.getDimensionPixelSize(R.dimen.d200dp)
        rvCommonLp.width = mActivity.resources.getDimensionPixelSize(R.dimen.d400dp)
        rvCommon.layoutParams = rvCommonLp
        rvCommon.layoutManager = LinearLayoutManager(mActivity)
        mCategoryListAdapter.bindToRecyclerView(rvCommon)
        rvCommon.addOnItemTouchListener(object : BaseSimpleClickListener() {

            //点击item，通过数据库获取其子类，然后放入到recyclerView中
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                //展开
                Timber.i("onItemChildClick position:  $position")
                val wkActivityBean = adapter?.getItem(position) as ActivitiesBean
                val wkActivity = wkActivityBean.wkActivity ?: return
                //如果没有下一层级的,要往数据库中取出来
                if (!wkActivityBean.hasSubItem()) {
                    val wkActivityId = wkActivity.baseObjId
                    LitePal.where("parentId=?", wkActivityId.toString())
                            .findAsync(WkActivity::class.java).listen {
                                Timber.i("size: ${it.size}")
                                it.forEach {
                                    Timber.i("it id is ${it.baseObjId}  wkActivityId $targetId")
                                    wkActivityBean.addSubItem(ActivitiesBean(it, wkActivityBean.wkLevel + 1, wkActivityBean))
                                }
                                adapter.expand(position)
                            }

                } else {//如果有
                    if (wkActivityBean.isExpanded)
                        adapter.collapse(position)
                    else
                        adapter.expand(position)
                }
            }

            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                Timber.i("onItemClick position:  $position")
                val wkActivityBean = adapter?.getItem(position) as? ActivitiesBean ?: return
                val moveId = wkActivityBean.wkActivity?.baseObjId ?: return
                if(moveId==targetId) ToastUtil.show("不能选自己")
                val intent = Intent()
                //变更后的父类别
                intent.putExtra(SchedulesBundleKey.ACTIVITY_PARENT_ID, moveId)
                //该类别所处的位子
                intent.putExtra(BundleKey.LIST_POSITION, position)
                targetFragment?.onActivityResult(RequestCode.ActivitiesInfoFragment_CHANGE_PARENTID, ResultCode.CategoryDialog, intent)
                disMiss()
            }
        })
        initRecycleData()
    }

    private fun initRecycleData() {
        //先取出最大的类别
        LitePal.where("parentId=?", WkActivity.NO_PARENT.toString())
                .findAsync(WkActivity::class.java).listen {
                    it.forEach {
                        cateGoryList.add(ActivitiesBean(it, 0))
                    }
                    mCategoryListAdapter.setNewData(cateGoryList)
                }
    }


    override fun onClick(v: View?) {
        Timber.i("点击")
    }

    /**
     * @param resId             添加的FooterView的布局文件Id
     * @param onClickListener   footerView对应的点击事件的监听
     * @param isRootOnClick     footerView该整个控件是否具有点击事件的监听
     * @param onClickIds        footerView 子View的点击事件的监听
     * */
    private fun getFooterView(resId: Int, onClickListener: View.OnClickListener? = null,
                              isRootOnClick: Boolean, vararg onClickIds: Int): View {
        val footer = LayoutInflater.from(mActivity)
                .inflate(resId, LinearLayout(mActivity))
        if (onClickListener != null) {
            if (isRootOnClick)
                footer.setOnClickListener(onClickListener)
            onClickIds.forEach {
                footer.findViewById<View>(it).setOnClickListener(onClickListener)
            }
        }
        return footer
    }
}