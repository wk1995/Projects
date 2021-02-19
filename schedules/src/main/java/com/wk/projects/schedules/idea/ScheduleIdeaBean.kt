package com.wk.projects.schedules.idea

import org.litepal.crud.LitePalSupport

/**
 *
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2020/6/7<br/>
 *      desc   : 想法实体类  <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 *      @param  createTime创建时间
 *      @param createContext 想法内容
 *      @param ideaFinishTime 完成时间
 * */
data class ScheduleIdeaBean(val ideaCreateTime:Long, var ideaContent:String,
                            var ideaFinishTime:Long=0) : LitePalSupport()