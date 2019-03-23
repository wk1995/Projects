package com.wk.projects.common.idea

import android.os.Bundle
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.R
import com.wk.projects.common.constant.CommonFilePath.COMMON_ROOT_PATH
import com.wk.projects.common.constant.CommonFilePath.ES_PATH
import com.wk.projects.common.helper.EditTextHelper
import com.wk.projects.common.helper.file.FileHelper
import com.wk.projects.common.helper.file.IFileStatusListener
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.common.resource.WkContextCompat
import com.wk.projects.common.ui.notification.ToastUtil
import kotlinx.android.synthetic.main.common_activity_idea.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.io.File

/**
 * <pre>
 *      author : wk
 *      e-mail : 122642603@qq.com
 *      time   : 2019/03/22
 *      GitHub : https://github.com/wk1995
 *      CSDN   : http://blog.csdn.net/qq_33882671
 *      desc   : 写新想法的界面
 * </pre>
 */
abstract class IdeaActivity : BaseProjectsActivity(), IFileStatusListener.IReadStatusListener {
    companion object {
        private const val IDEA_FILE_NAME = "/idea.txt"
    }

    private val ideaFilePath by lazy { ES_PATH + COMMON_ROOT_PATH + getModuleName() + IDEA_FILE_NAME }
    private val fileHelper by lazy { FileHelper.getInstance() }
    private val ideaFile by lazy { File(ideaFilePath) }
    private val editTextHelper by lazy { EditTextHelper.getInstance() }
    private var ideaIisChange = false
    override fun initResLay() = R.layout.common_activity_idea
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        //这时候etIdea的内容其实是为空的
//        editTextHelper.showLastPosition(etIdea)
        //读取文件
        fileHelper.readIO(ideaFile, this)
        etIdea.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ideaIisChange = true
                editTextHelper.showLastPosition(etIdea)
                super.onTextChanged(s, start, before, count)
            }
        })
        btSaveIdea.setOnClickListener {
            if (!ideaIisChange) return@setOnClickListener
            //保存
            val idea = etIdea.text.toString()
            Observable.just(ideaFile).map {
                fileHelper.write(it, idea)
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Any>() {
                        override fun onNext(t: Any?) {
                            Timber.i("onNext: $t")
                        }

                        override fun onCompleted() {
                            Timber.i("onCompleted")
                            ToastUtil.show(WkContextCompat.getString(R.string.common_str_save_successful), ToastUtil.LENGTH_SHORT)
                        }

                        override fun onError(e: Throwable?) {
                            Timber.i("onError: ${e?.message}")
                        }
                    })

        }
    }

    override fun readResult(result: String?) {
        etIdea.setText(result)
    }

    override fun readFinish() {
        Timber.i("readFinish")
    }

    override fun readError(e: Throwable?) {
        Timber.i("onError: ${e?.message}")
    }

    abstract fun getModuleName(): String
}
