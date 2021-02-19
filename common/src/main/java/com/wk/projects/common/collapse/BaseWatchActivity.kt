package com.wk.projects.common.collapse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import android.widget.FrameLayout
import android.widget.ScrollView
import com.wk.projects.common.communication.constant.BundleKey.FILE
import com.wk.projects.common.helper.file.FileHelper
import com.wk.projects.common.helper.file.IFileStatusListener
import timber.log.Timber
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
abstract class BaseWatchActivity : AppCompatActivity(), IFileStatusListener.IReadStatusListener {
    private val fileHelper by lazy { FileHelper.getInstance() }
    private var appCompatTextView: AppCompatTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        appCompatTextView = AppCompatTextView(this)
        appCompatTextView?.layoutParams = lp
        val scroll = ScrollView(this)
        scroll.layoutParams = lp
        scroll.addView(appCompatTextView)
        setContentView(scroll)
        val file = intent.getSerializableExtra(FILE) as? File
        fileHelper.readIO(file, this)
    }

    override fun readResult(result: String?) {
        appCompatTextView?.text = result
    }

    override fun readFinish() {
    }

    override fun readError(e: Throwable?) {
        Timber.i("readError: ${e?.message}")
    }
}