package com.wk.projects.activities.data

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wk.projects.activities.R

/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/6/30<br/>
 *      coordinateDesc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
class CoordinateAdapter : BaseQuickAdapter<ActivitiesInfoFragment.LocationBean, BaseViewHolder>(R.layout.activities_coordinate_list_item) {
    override fun convert(helper: BaseViewHolder?, item: ActivitiesInfoFragment.LocationBean?) {
        item?.run {
            helper?.setText(R.id.tvDescCoordinate,mCoordinateId.coordinateDesc)
                    ?.setText(R.id.tvTimeCoordinate,time.toString())
        }
    }
}