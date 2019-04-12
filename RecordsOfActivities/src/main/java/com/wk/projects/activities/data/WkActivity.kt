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
 *      desc   : 活动所属的类别
 * </pre>
 */

/**
 * @param itemName  :   类别名称
 * @param createTime:   类别创建时间
 * @param parentId  :   所属的类别 -1表示没有
 * @param isSystem  :   是否能够被删除
 *
 * */
data class WkActivity(@Column(nullable = false) var itemName: String,
                      @Column(nullable = true) var createTime: Long? = null,
                      @Column(nullable = false) var parentId: Long = NO_PARENT,
                      @Column(nullable = false) var isSystem: Boolean = false)
    : LitePalSupport() {

    companion object {
        const val NO_PARENT = -1L
    }

    public override fun getBaseObjId(): Long {
        return super.getBaseObjId()
    }
}