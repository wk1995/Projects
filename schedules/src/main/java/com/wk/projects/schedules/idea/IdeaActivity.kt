package com.wk.projects.schedules.idea

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.helper.file.IFileStatusListener
import com.wk.projects.common.ui.WkToast
import com.wk.projects.schedules.R
import kotlinx.android.synthetic.main.schedules_activity_idea.*
import org.litepal.LitePal
import timber.log.Timber

@Route(path = ARoutePath.IdeaActivity)
class IdeaActivity : BaseProjectsActivity(), IFileStatusListener.IReadStatusListener, View.OnClickListener {
    private val adapter=IdeaAdapter()
    override fun initResLayId() = R.layout.schedules_activity_idea
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        rcIdeaItemList.layoutManager=LinearLayoutManager(this)
        LitePal.findAllAsync(ScheduleIdeaBean::class.java).listen {
            rcIdeaItemList.adapter=adapter
            adapter.setScheduleIdeaBeans(it)
        }
        btSaveIdea.setOnClickListener {
            //保存
            val idea = etIdeaContent.text.toString()
            val newS= ScheduleIdeaBean(System.currentTimeMillis(),idea)
            newS.saveAsync().listen {
                if(it){
                    adapter.addScheduleIdeaBean(newS)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btSaveIdea -> {
                val ideaContent=etIdeaContent.text
                if(TextUtils.isEmpty(ideaContent.trim())){
                    WkToast.showToast("idea不能为empty")
                    return
                }
                val idea=ScheduleIdeaBean(System.currentTimeMillis(),ideaContent.toString())
                //todo
                idea.saveAsync().listen {
                    WkToast.showToast(if(it)"保存成功" else "保存失败")
                    if(it){
                        rcIdeaItemList.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun readResult(result: String?) {
        etIdeaContent.setText(result)
    }

    override fun readFinish() {
        Timber.i("readFinish")
    }

    override fun readError(e: Throwable?) {
        Timber.i("onError: ${e?.message}")
    }
}
