package com.sbnh.my.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.my.databinding.FragmentMyBinding
import com.sbnh.my.viewbinding.MyViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 15:42
 * 更新时间: 2022/6/13 15:42
 * 描述:我的fragment
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY)
class MyFragment : BaseCompatFragment<FragmentMyBinding, MyViewModel>() {
    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun initObserve() {
    }
}