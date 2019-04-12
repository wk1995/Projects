package com.wk.projects.activities.data.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wk.projects.activities.R
import com.wk.projects.activities.data.WkActivity
import com.wk.projects.activities.data.add.adapter.ActivitiesBean
import com.wk.projects.activities.data.add.adapter.CategoryListAdapter
import com.wk.projects.common.BaseDialogFragment
import com.wk.projects.common.listener.BaseSimpleClickListener
import org.litepal.LitePal
import timber.log.Timber

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/4/4
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 类别的列表
 * </pre>
 */
class CategoryDialog : BaseDialogFragment(), View.OnClickListener {

    companion object {
        fun create(bundle: Bundle? = null): CategoryDialog {
            val mCategoryDialog = CategoryDialog()
            mCategoryDialog.arguments = bundle
            return mCategoryDialog
        }
    }

    private val cateGoryList by lazy { ArrayList<ActivitiesBean>() }
    private val mCategoryListAdapter by lazy { CategoryListAdapter(ArrayList()) }
    override fun initResLayId() = R.layout.common_only_recycler

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        val rvCommon = rootView as? RecyclerView ?: return
        rvCommon.layoutManager = LinearLayoutManager(mActivity)
        mCategoryListAdapter.bindToRecyclerView(rvCommon)
        rvCommon.addOnItemTouchListener(object : BaseSimpleClickListener() {

            //点击item，通过数据库获取其子类，然后放入到recyclerView中
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                Timber.i("onItemChildClick position:  $position")
                val wkActivityBean = adapter?.getItem(position) as ActivitiesBean
                val wkActivity = wkActivityBean.wkActivity
                LitePal.where("parentId=?", wkActivity.baseObjId.toString())
                        .findAsync(WkActivity::class.java).listen {
                            it.forEach {
                                wkActivityBean.addSubItem(ActivitiesBean(it, wkActivityBean.wkLevel + 1))
                            }

                        }
            }

            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                super.onItemClick(adapter, view, position)
                Timber.i("onItemClick position:  $position")
                val wkActivityBean = adapter?.getItem(position) as ActivitiesBean
                val wkActivity = wkActivityBean.wkActivity

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

                   /* mCategoryListAdapter.addFooterView(
                            getFooterView(R.layout.activities_category_list_item, this, true)
                    )*/
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