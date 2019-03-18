package com.wk.projects.activities.idea

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.configuration.ConfigureKey
import com.wk.projects.common.configuration.WkProjects
import com.wk.projects.common.constant.ARoutePath
import com.wk.projects.common.constant.CommonFilePath.COMMON_ROOT_PATH
import com.wk.projects.common.constant.CommonFilePath.ES_PATH
import com.wk.projects.common.helper.file.FileHelper
import com.wk.projects.common.helper.file.IFileStatusListener
import com.wk.projects.common.listener.BaseTextWatcher
import com.wk.projects.activities.R
import kotlinx.android.synthetic.main.schedules_activity_idea.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.io.*

@Route(path = ARoutePath.IdeaActivity)
class IdeaActivity : BaseProjectsActivity(), IFileStatusListener.IReadStatusListener {
    companion object {
        private const val IDEA_FILE_NAME = "/idea.txt"
        private val moduleName = WkProjects.getConfiguration<String>(ConfigureKey.MODULE_NAME)
        val IDEA_FILE_PATH = ES_PATH + COMMON_ROOT_PATH + moduleName + IDEA_FILE_NAME
    }

    private val fileHelper by lazy { FileHelper.getInstance() }
    private val ideaFile by lazy { File(IDEA_FILE_PATH) }

    private var isSave = false
    override fun initResLayId() = R.layout.schedules_activity_idea
    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        //读取文件
        fileHelper.readIO(ideaFile,this)
        etIdea.addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isSave = false
                super.onTextChanged(s, start, before, count)
            }
        })
        btSaveIdea.setOnClickListener {
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
}
