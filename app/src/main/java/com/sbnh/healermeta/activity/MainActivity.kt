package com.sbnh.healermeta.activity

import com.sbnh.comm.base.activity.BaseActivity
import com.sbnh.comm.compat.ToastCompat
import com.sbnh.healermeta.databinding.ActivityMainBinding
import com.sbnh.healermeta.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun initData() {


    }

    override fun initEvent() {
        mViewBinding.atvContent.setOnClickListener {
            ToastCompat.create().showToast("我是吐司")
        }
    }

    override fun initObserve() {


    }


}