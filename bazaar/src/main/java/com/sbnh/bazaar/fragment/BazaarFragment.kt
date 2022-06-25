package com.sbnh.bazaar.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.databinding.FragmentBazaarBinding
import com.sbnh.bazaar.viewmodel.BazaarViewModel
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.other.arouter.ARouterConfig

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:38
 * 更新时间: 2022/6/13 16:38
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR)
class BazaarFragment : BaseCompatFragment<FragmentBazaarBinding, BazaarViewModel>() {
    override fun getViewBinding(): FragmentBazaarBinding =
        FragmentBazaarBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BazaarViewModel> = BazaarViewModel::class.java

    override fun initView() {
        mEmptyLayout?.setText(DataCompat.getResString(com.sbnh.comm.R.string.bazaar_not_open))
        mEmptyLayout?.show()
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun initObserve() {
        super.initObserve()
    }

    override fun isLoadEmptyView(): Boolean = true
}