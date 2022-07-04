package com.sbnh.bazaar.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.databinding.FragmentBazaarContentBinding
import com.sbnh.bazaar.viewmodel.BazaarContentViewModel
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.other.arouter.ARouterConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 9:38
 * 更新时间: 2022/7/4 9:38
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_CONTENT)
class BazaarContentFragment :
    BaseCompatFragment<FragmentBazaarContentBinding, BazaarContentViewModel>() {
    override fun getViewBinding(): FragmentBazaarContentBinding =
        FragmentBazaarContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BazaarContentViewModel> =
        BazaarContentViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}