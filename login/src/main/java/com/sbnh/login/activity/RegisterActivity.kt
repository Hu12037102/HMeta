package com.sbnh.login.activity

import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.login.databinding.ActivityRegisterBinding
import com.sbnh.login.viewmodel.RegisterViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 14:49
 * 更新时间: 2022/6/15 14:49
 * 描述:
 */
class RegisterActivity : BaseCompatActivity<ActivityRegisterBinding, RegisterViewModel>() {


    override fun getViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java
    override fun initView() {

    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}