package com.wk.projects.activities.data

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/2/25
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
data class WkActivity(@Column(nullable = false) var itemName: String,
                      @Column(nullable = true) var createTime: Long? = null)
    : LitePalSupport(){

    public override  fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}