package com.sbnh.bazaar.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.databinding.FragmentBazaarDetailsContentBinding
import com.sbnh.bazaar.viewmodel.BazaarDetailsContentViewModel
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.other.arouter.ARouterConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 17:13
 * 更新时间: 2022/7/12 17:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_DETAILS_CONTENT)
class BazaarDetailsContentFragment :
    BaseCompatFragment<FragmentBazaarDetailsContentBinding, BazaarDetailsContentViewModel>() {
    override fun getViewBinding(): FragmentBazaarDetailsContentBinding =
        FragmentBazaarDetailsContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BazaarDetailsContentViewModel> =
        BazaarDetailsContentViewModel::class.java

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}