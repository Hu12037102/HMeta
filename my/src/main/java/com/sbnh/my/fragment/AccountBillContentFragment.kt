package com.sbnh.my.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.my.AccountBillEntity
import com.sbnh.comm.entity.request.RequestAccountBillEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.my.adapter.AccountBillContentAdapter
import com.sbnh.my.databinding.FragmentAccountBillContentBinding
import com.sbnh.my.viewmodel.AccountBillContentViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/15 9:54
 * 更新时间: 2022/7/15 9:54
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_ACCOUNT_BILL_CONTENT)
class AccountBillContentFragment :
    BaseCompatFragment<FragmentAccountBillContentBinding, AccountBillContentViewModel>() {
    private var mAdapter: AccountBillContentAdapter? = null
    private val mData = ArrayList<AccountBillEntity>()
    private var mType: Int = SelectorTabEntity.AccountBill.TYPE_ALL
    override fun getViewBinding(): FragmentAccountBillContentBinding =
        FragmentAccountBillContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<AccountBillContentViewModel> =
        AccountBillContentViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(DataCompat.checkContext(context))
    }

    override fun initData() {
        mType = arguments?.getInt(ARouterConfig.Key.TYPE) ?: SelectorTabEntity.AccountBill.TYPE_ALL
        mAdapter = AccountBillContentAdapter(DataCompat.checkContext(context), mData)
        mViewBinding.rvData.adapter = mAdapter
        loadSmartData()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadAccountBills(
            RequestAccountBillEntity(
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mType,
                mViewModel.mLastTime
            )
        )
    }

    override fun initEvent() {

    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mAccountBillLiveData.observe(this) {
            val data = BaseEntity.getPagerData(it)
            UICompat.notifyAdapterDateChanged(
                mEmptyLayout,
                mAdapter,
                mViewModel.isRefresh,
                mData,
                data
            )
        }
    }
}