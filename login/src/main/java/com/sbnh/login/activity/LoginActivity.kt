package com.sbnh.login.activity

import androidx.lifecycle.ViewModelProvider
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.login.databinding.ActivityLoginBinding
import com.sbnh.login.viewmodel.LoginViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 19:51
 * 更新时间: 2022/6/14 19:51
 * 描述:
 */
class LoginActivity : BaseCompatActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun initView() {
    }


    override fun initData() {
    }

    override fun initEvent() {
    }
}