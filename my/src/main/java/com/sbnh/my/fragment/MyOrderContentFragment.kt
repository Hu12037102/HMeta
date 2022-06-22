package com.sbnh.my.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.RequestOrderListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.my.adapter.MyOrderAdapter
import com.sbnh.my.databinding.FragmentMyOrderContentBinding
import com.sbnh.my.viewmodel.MyOrderContentViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 12:14
 * 更新时间: 2022/6/22 12:14
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_ORDER_CONTENT)
class MyOrderContentFragment :
    BaseCompatFragment<FragmentMyOrderContentBinding, MyOrderContentViewModel>() {
    private val requestEntity = RequestOrderListEntity()
    private val mData = ArrayList<OrderEntity>()
    private var mAdapter: MyOrderAdapter? = null
    override fun getViewBinding(): FragmentMyOrderContentBinding =
        FragmentMyOrderContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyOrderContentViewModel> =
        MyOrderContentViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        val state = arguments?.getInt(ARouterConfig.Key.ID, Contract.UNKNOWN_INT_VALUE)
        if (state != Contract.UNKNOWN_INT_VALUE) {
            requestEntity.status = state
        }
        mAdapter = MyOrderAdapter(requireContext(), mData)
        mViewBinding.rvData.adapter = mAdapter
        mViewModel.loadMyOrderList(requestEntity)
    }

    override fun initEvent() {
        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
                ARoutersActivity.startOrderDetailsActivity(mData[position].id)

            }

            override fun longClickItem(view: View, position: Int) {
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mOrderListLiveData.observe(this) {
            val baseEntity = BaseEntity.getData(it)
            val data = BasePagerEntity2.getData(baseEntity)
            if (CollectionCompat.notEmptyList(data)) {
                mData.addAll(data!!)
                mAdapter?.notifyDataSetChanged()
            }
        }
    }
}