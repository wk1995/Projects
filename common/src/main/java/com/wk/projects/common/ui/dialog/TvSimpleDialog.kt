package com.wk.projects.common.ui.dialog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wk.projects.common.R
import com.wk.projects.common.communication.constant.BundleKey

class TvSimpleDialog:BaseSimpleDialog() {
    lateinit var contentView:TextView

    companion object{
        fun create(bundle:Bundle?=null):TvSimpleDialog{
            val mTvSimpleDialog=TvSimpleDialog()
            mTvSimpleDialog.arguments=bundle
            return mTvSimpleDialog
        }
    }

    override fun bindView(savedInstanceState: Bundle?, rootView: View?) {
        super.bindView(savedInstanceState, rootView)
        val okString=arguments?.getString(BundleKey.DIALOG_OK)
        val title=arguments?.getString(BundleKey.DIALOG_TITLE)
        val cancel=arguments?.getString(BundleKey.DIALOG_CANCEL)
        setString(btnComSimpleDialogOk,okString)
        setString(tvComSimpleDialogTheme,title)
        setString(btnComSimpleDialogCancel,cancel)

    }

    private fun setString(tv:TextView,content: String?){
        if(content!=null)
            tv.text=content
    }


    override fun initViewSubLayout()= R.layout.common_only_text

    override fun initVSView(vsView: View) {
        contentView=vsView.findViewById(R.id.tvCommon)
        val content=arguments?.getString(BundleKey.DIALOG_CONTENT)
        if(content!=null)
            contentView.text=content
    }
    private class Builder{
        private var title:String?=null
        private var content:String?=null
        private var cancelString:String?=null
        private var okString:String?=null

        fun setTitle(title:String?):Builder{
            this.title=title
            return this
        }

        fun setContent(content:String?):Builder{
            this.content=content
            return this
        }

        fun setCancelString(cancel:String?):Builder{
            this.cancelString=cancel
            return this
        }

        fun setOkString(okString:String?):Builder{
            this.okString=okString
            return this
        }

        fun build():TvSimpleDialog{
            val argument=Bundle()
            argument.putString(BundleKey.DIALOG_CANCEL,cancelString)
            argument.putString(BundleKey.DIALOG_OK,okString)
            argument.putString(BundleKey.DIALOG_CONTENT,content)
            argument.putString(BundleKey.DIALOG_TITLE,title)
            return create(argument)
        }
    }
}