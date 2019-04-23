package com.wk.projects.common.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.wk.projects.common.configuration.WkProjects

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/20
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
object NotificationBuilder {
    private val context by lazy { WkProjects.getContext() }
    private val notifyManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "1"
            val channelName = "SkyWalker"
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            //是否在桌面icon右上角展示小红点
            notificationChannel.enableLights(true)
            //小红点颜色
            notificationChannel.lightColor = Color.GREEN
            //是否在久按桌面图标时显示此渠道的通知
            notificationChannel.setShowBadge(true)
            notifyManager.createNotificationChannel(notificationChannel)
        }
    }

    @JvmStatic
    fun getBuilder(channelId: String) = NotificationCompat.Builder(context, channelId)

    @JvmStatic
    fun showNotification(id: Int, notification: Notification) {
        notifyManager.notify(id, notification)
    }

}