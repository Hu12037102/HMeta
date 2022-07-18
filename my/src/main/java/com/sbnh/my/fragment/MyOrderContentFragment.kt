package com.sbnh.my.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.order.OrderEntity
import com.sbnh.comm.entity.order.RequestOrderListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.my.adapter.MyOrderAdapter
import com.sbnh.my.databinding.FragmentMyOrderContentBinding
import com.sbnh.my.viewmodel.MyOrderContentViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 12:14
 * 更新时间: 2022/6/22 12:14
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_ORDER_CONTENT)
class MyOrderContentFragment :
    BaseCompatFragment<FragmentMyOrderContentBinding, MyOrderContentViewModel>() {
    // private val requestEntity = RequestOrderListEntity(mViewModel.mPagerNum,mViewModel.mPagerSize)
    private var mOrderStatus: Int? = null
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
            mOrderStatus = state
        }
        mAdapter = MyOrderAdapter(requireContext(), mData)
        mViewBinding.rvData.adapter = mAdapter
        // mViewModel.loadMyOrderList(requestEntity)
        loadSmartData()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mViewModel.loadMyOrderList(
            RequestOrderListEntity(
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mOrderStatus
            ),true
        )
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

    override fun isLoadEmptyView(): Boolean = true

    override fun initObserve() {
        super.initObserve()
        mViewModel.mOrderListLiveData.observe(this) {
            val baseEntity = BaseEntity.getData(it)
            val data = BasePagerEntity2.getData(baseEntity)
            /*  if (mViewModel.isRefresh) {
                  mData.clear()
              }
              if (CollectionCompat.notEmptyList(data)) {
                  mData.addAll(data!!)
              }
              mAdapter?.notifyDataSetChanged()
              if (CollectionCompat.isEmptyList(mData)) mEmptyLayout?.show() else mEmptyLayout?.hide()*/
            UICompat.notifyAdapterAddDateChanged(
                mEmptyLayout,
                mAdapter,
                mViewModel.isRefresh,
                mData,
                data
            )
        }
    }
}