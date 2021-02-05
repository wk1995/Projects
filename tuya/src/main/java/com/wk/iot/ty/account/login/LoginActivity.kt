package com.wk.iot.ty.account.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.tuya.smart.android.user.api.ILoginCallback
import com.tuya.smart.android.user.bean.User
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.wk.iot.ty.MainActivity
import com.wk.iot.ty.databinding.TuyaLoginActivityBinding
import com.wk.projects.common.BaseProjectsActivity
import com.wk.projects.common.log.WkLog
import com.wk.projects.common.ui.WkToast
import timber.log.Timber

/**
 * 与涂鸦智能账号不互通，需要注册
 * */
class LoginActivity : BaseProjectsActivity() {

    private lateinit var btnLogin: Button
    private lateinit var etTuyaLoginAccount: EditText
    private lateinit var etTuyaLoginPassword: EditText
    private val bind by lazy { TuyaLoginActivityBinding.inflate(layoutInflater) }

    override fun initResLayId() = bind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        btnLogin = bind.btnLogin
        etTuyaLoginAccount = bind.etTuyaLoginAccount
        etTuyaLoginPassword = bind.etTuyaLoginPassword
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnLogin -> {
                val account = etTuyaLoginAccount.text.toString()
                val password = etTuyaLoginPassword.text.toString()
                Timber.i("wk")
                WkLog.d("account: $account  password:  $password")
                TuyaHomeSdk.getUserInstance().loginWithPhonePassword("86", account, password, object : ILoginCallback {
                    override fun onSuccess(user: User?) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onError(code: String?, error: String?) {
                        WkToast.showToast("code: $code  error:  $error")
                    }
                });
            }
        }
    }
}