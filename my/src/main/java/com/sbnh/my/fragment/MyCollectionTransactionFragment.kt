package com.sbnh.my.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.my.databinding.FragmentMyCollectionTransactionBinding
import com.sbnh.my.viewmodel.MyCollectionTransactionViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 15:13
 * 更新时间: 2022/7/18 15:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION_TRANSACTION)
class MyCollectionTransactionFragment :
    BaseCompatFragment<FragmentMyCollectionTransactionBinding, MyCollectionTransactionViewModel>() {
    override fun getViewBinding(): FragmentMyCollectionTransactionBinding =
        FragmentMyCollectionTransactionBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyCollectionTransactionViewModel> =
        MyCollectionTransactionViewModel::class.java

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}