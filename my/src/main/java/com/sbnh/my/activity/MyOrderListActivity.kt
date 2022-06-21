package com.sbnh.my.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.entity.order.OrderTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.my.adapter.MyOrderTabAdapter
import com.sbnh.my.databinding.ActivityMyOrderListBinding
import com.sbnh.my.viewmodel.MyOrderListViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 19:36
 * 更新时间: 2022/6/21 19:36
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_MY_ORDER_LIST)
class MyOrderListActivity : BaseCompatActivity<ActivityMyOrderListBinding, MyOrderListViewModel>() {
    private val mTabData = ArrayList<OrderTabEntity>()
    private var mTabAdapter: MyOrderTabAdapter? = null

    override fun getViewBinding(): ActivityMyOrderListBinding =
        ActivityMyOrderListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyOrderListViewModel> = MyOrderListViewModel::class.java

    override fun initView() {
        mViewBinding.rvTab.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData() {
        mTabData.addAll(mViewModel.createOrderTab())
        mTabAdapter = MyOrderTabAdapter(this, mTabData)
        mViewBinding.rvTab.adapter = mTabAdapter
    }

    override fun initEvent() {
    }
}