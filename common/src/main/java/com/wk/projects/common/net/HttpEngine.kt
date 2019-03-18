package com.wk.projects.common.net

import android.os.Handler
import android.os.Looper
import android.support.v4.content.LocalBroadcastManager
import com.wk.projects.common.configuration.ConfigureKey.HOST
import com.wk.projects.common.configuration.ConfigureKey.INTERCEPTORS
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.net.bean.ApkBean
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *      author : wk
 *      e-mail : 1226426603@qq.com
 *      time   : 2018/09/25
 *      desc   :
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 * </pre>
 */
abstract class HttpEngine(queryMap: HashMap<String, String>, val any: Any?) {
    protected val switchHandler by lazy { Handler(Looper.getMainLooper()) }
    protected val queryMap by lazy { HashMap<String, String>() }
    private val apkBean: ApkBean? by lazy {
        any as? ApkBean
    }
    private val lbm by lazy { LocalBroadcastManager.getInstance(WkProjects.getContext()) }
    /*   private val mBuilder by lazy {
           val channelId = "1"
           NotificationCompat.Builder(SkyWalker.getContext(), channelId)
                   //通知首次出现在通知栏，带上升动画效果的
                   .setTicker("捷账宝新版本诚邀体验")
                   .setContentTitle("wk")
                   .setOngoing(true)

       }*/

    /*  private val mBuilder by lazy {
          val channelId = "1"
          NotificationBuilder.getBuilder(channelId)
                  //通知首次出现在通知栏，带上升动画效果的
                  .setTicker("SPS诚邀体验")
                  .setContentTitle("SPS")
  //                .setContentIntent(getDefaultIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
  //              .setNumber(number) //设置通知集合的数量
                  .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                  .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                  .setPriority(NotificationManagerCompat.IMPORTANCE_MAX) //设置该通知优先级
                  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                  .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
  //                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                  //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                  .setSmallIcon(R.mipmap.spslogoa01_01)//设置通知小ICON
      }*/


    @Suppress("UNUSED")
    companion object {
        val rootUrl = WkProjects.getConfiguration<String>(HOST)
        const val QUERY_OPERATION = "query"
        const val TEST_OPERATION = "test"
        const val DOWNLOAD_OPERATION = "downLoad"
        private const val UPDATE_STEP = 1000
    }

    init {
        this.queryMap.putAll(queryMap)
    }

    protected val okHttpClient: OkHttpClient by lazy {
        val cacheSize = 10 * 1024 * 1024L
        var okHttpClientBuilder = OkHttpClient.Builder()
        val interceptors = WkProjects.getConfiguration<ArrayList<Interceptor>>(INTERCEPTORS)
        if (interceptors?.isNotEmpty() == true)
            interceptors.forEach {
                okHttpClientBuilder = okHttpClientBuilder.addInterceptor(it)
            }
        okHttpClientBuilder
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(Cache(WkProjects.getContext().externalCacheDir?.absoluteFile
                        ?: throw Exception(""), cacheSize))
                .build()
    }


    //流合成文件
    protected fun streamToFile(responseBody: ResponseBody?) {
        val inputStream = responseBody?.byteStream()
        val buff = ByteArray(1024)
        var out: FileOutputStream? = null

        try {
            val apkBean = any as? ApkBean ?: throw java.lang.Exception("传入的apkBean为null")
            // /storage/emulated/0
            val apkPath =
//                    FilePath.downLoadPath +
                    "/apk/"
            val storeFile = File(apkPath + apkBean.apkName + ".apk")
            if (!storeFile.exists())
                storeFile.parentFile.mkdirs()
            else
                storeFile.delete()
            out = FileOutputStream(storeFile)
            var len: Int = inputStream?.read(buff) ?: -1
            val totalLength = responseBody?.contentLength() ?: 1
            var currentLength = 0L
            while (len != -1) {
                out.write(buff, 0, len)
                currentLength += len
                val progress = (currentLength * 100 / (totalLength)).toInt()
                updateDownLoadProgress(progress, apkBean)
                len = inputStream?.read(buff) ?: -1
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            out?.close()
            inputStream?.close()
        }
    }

    private var updateTime = 0L
    private fun updateDownLoadProgress(progress: Int, apkBean: ApkBean) {
        /*   Timber.d("144 progress $progress")
           if (System.currentTimeMillis() - updateTime > UPDATE_STEP || progress == 100) {
               apkBean.progress=progress
               if (progress == 100) {
                   mBuilder.setContentText("下载完成")
                   apkBean.state = STATE_COMPLETE
               } else
                   mBuilder.setContentText("正在下载:$progress%").setProgress(100, progress, false)
               updateTime=System.currentTimeMillis()
               NotificationBuilder.showNotification(1, mBuilder.build())
               val intent = Intent(APP_FLUSH_PROGRESS)
               intent.putExtra(BundleExtras.DOWNLOAD_FILE, apkBean)
               lbm.sendBroadcast(intent)
           }*/
    }

    abstract fun operateAsyHttp()
    abstract fun cancel()


}