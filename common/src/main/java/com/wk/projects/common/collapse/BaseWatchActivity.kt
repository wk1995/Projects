package com.wk.projects.common.collapse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.widget.FrameLayout
import android.widget.ScrollView
import com.wk.projects.common.communication.constant.BundleKey.FILE
import java.io.*

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2018/11/28
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   :
 * </pre>
 */
abstract class BaseWatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        val appCompatTextView = AppCompatTextView(this)
        appCompatTextView.layoutParams = lp
        val scroll = ScrollView(this)
        scroll.layoutParams = lp
        scroll.addView(appCompatTextView)
        setContentView(scroll)
        val file = intent.getSerializableExtra(FILE) as? File
        if (file == null) {
            appCompatTextView.text = "空文件"
            return
        }
        val sb = StringBuilder()
        var br: BufferedReader? = null
        try {
            br = BufferedReader(InputStreamReader(FileInputStream(file)))

            var tmp = br.readLine()
            while (tmp != null) {
                sb.append(tmp)
                sb.append("\r\n")
                tmp = br.readLine()
            }
        } catch (e: IOException) {
            sb.append("读取文件发生异常： \r\n ${e.message}")
        } catch (e: FileNotFoundException) {
            sb.append("未找到文件：\r\n ${file.path}")
        } catch (e: Exception) {
            sb.append("发生异常： \r\n ${e.message}")
        } finally {
            br?.close()
        }
        appCompatTextView.text = sb.toString()
    }
}