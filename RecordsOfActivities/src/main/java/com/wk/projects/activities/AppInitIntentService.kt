package com.wk.projects.activities

import android.app.IntentService
import android.content.Intent
import com.wk.projects.activities.data.WkActivity
import com.wk.projects.activities.data.WkActivity.NO_PARENT
import com.wk.projects.common.configuration.ConfigureKey.INIT_ACTIVITIES
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.resource.WkContextCompat
import org.litepal.LitePal
import timber.log.Timber

/**
 * 先建立一些类别
 *
 * */
class AppInitIntentService : IntentService("AppInitIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        val initActivities = WkProjects.getConfiguration<Boolean>(INIT_ACTIVITIES)
        if (initActivities == true) return
        val list = intArrayOf(R.string.common_entertainment, R.string.common_study, R.string.common_daily)
        list.forEach {
            val activityName = WkContextCompat.getString(it) ?: return@forEach
            if (LitePal.where("itemName=?", activityName).find(WkActivity::class.java).size <= 0) {
                Timber.d("建立  $activityName 类")
               val result= WkActivity(
                        activityName,
                        System.currentTimeMillis(), NO_PARENT, true).save()
                Timber.d("$activityName 建立成功： $result")
            }
        }
        WkProjects.setConfiguration(INIT_ACTIVITIES, true)
    }


}
