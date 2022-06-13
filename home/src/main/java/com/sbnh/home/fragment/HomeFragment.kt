package com.sbnh.home.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.home.databinding.FragmentHomeBinding
import com.sbnh.home.viewmodel.HomeViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:13
 * 更新时间: 2022/6/13 16:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.Home.FRAGMENT_HOME)
class HomeFragment : BaseCompatFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java
    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun initObserve() {
    }


}