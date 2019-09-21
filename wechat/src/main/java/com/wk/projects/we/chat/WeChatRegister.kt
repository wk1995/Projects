package com.wk.projects.we.chat

import com.tencent.mm.opensdk.constants.ConstantsAPI
import android.content.IntentFilter
import android.provider.UserDictionary.Words.APP_ID
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject


/**
 * <pre>
 *      author : wk <br/>
 *      e-mail : 1226426603@qq.com<br/>
 *      time   : 2019/9/21<br/>
 *      desc   :   <br/>
 *      GitHub : https://github.com/wk1995 <br/>
 *      CSDN   : http://blog.csdn.net/qq_33882671 <br/>
 * </pre>*/
@Suppress("unused")
class WeChatRegister private constructor() {
    private var api: IWXAPI? = null

    private val wxRegister by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // 将该app注册到微信
                registerWX(context ?: return)
            }
        }
    }

    private object WeChatRegisterVH {
        val INSTANCE = WeChatRegister()
    }

    companion object {
        // APP_ID 替换为你的应用从官方网站申请到的合法appID
        private const val APP_ID = "wx88888888"

        fun getInstance() = WeChatRegisterVH.INSTANCE
    }


    fun registerWX(context: Context) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, APP_ID, true)
        }
        // 通过WXAPIFactory工厂，获取IWXAPI的实例,IWXAPI 是第三方app和微信通信的openApi接口
        api?.registerApp(APP_ID)
    }

    fun unregisterApp() {
        api?.unregisterApp()
    }

    fun dynamicRegisterWx(context: Context) {
        //建议动态监听微信启动广播进行注册到微信
        context.registerReceiver(wxRegister, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }

    fun dynamicUnRegisterWx(context: Context) {
        context.unregisterReceiver(wxRegister)
        unregisterApp()
    }


    fun sendReq(text:String,wxSceneType:Int) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        val textObj = WXTextObject()
        textObj.text = text

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage()
        msg.mediaObject = textObj
        msg.description = text

        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()  //transaction字段用与唯一标示一个请求
        req.message = msg
        req.scene = wxSceneType
        //调用api接口，发送数据到微信
        api?.sendReq(req)
    }

    fun sendResp() {

    }

    /**支持分享到朋友圈*/
    fun isSupportWxFriend()=api?.wxAppSupportAPI?:-1 >= Build.TIMELINE_SUPPORTED_SDK_INT


}