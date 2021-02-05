package com.wk.iot.ty.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.tuya.smart.home.sdk.TuyaHomeSdk
import com.wk.iot.ty.MainActivity
import com.wk.iot.ty.R
import com.wk.iot.ty.databinding.TuyaAccountGuideActivityBinding
import com.wk.iot.ty.account.login.LoginActivity
import com.wk.projects.common.BaseProjectsActivity

/**登录，注册入口*/
class AccountActivity : BaseProjectsActivity(), View.OnClickListener {

    private lateinit var btnGoLogin: Button
    private lateinit var btnRegister: Button

    private val bind by lazy { TuyaAccountGuideActivityBinding.inflate(layoutInflater) }


    override fun initResLayId() = bind.root

    override fun bindView(savedInstanceState: Bundle?, mBaseProjectsActivity: BaseProjectsActivity) {
        btnGoLogin = bind.btnGoLogin
        btnRegister = bind.btnGoRegister
        btnGoLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)

    }

    override fun initContentView() {
        super.initContentView()
        if (TuyaHomeSdk.getUserInstance().isLogin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGoLogin -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btnGoRegister -> {

            }
        }
    }
}