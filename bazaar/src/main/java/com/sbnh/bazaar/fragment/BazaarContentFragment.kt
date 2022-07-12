package com.sbnh.bazaar.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.adapter.BazaarContentAdapter
import com.sbnh.bazaar.databinding.FragmentBazaarContentBinding
import com.sbnh.bazaar.viewmodel.BazaarContentViewModel
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.bazaar.BazaarEntity
import com.sbnh.comm.entity.request.RequestBazaarEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.utils.LogUtils
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/4 9:38
 * 更新时间: 2022/7/4 9:38
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_CONTENT)
class BazaarContentFragment :
    BaseCompatFragment<FragmentBazaarContentBinding, BazaarContentViewModel>() {
    private var mId: String = ""
    private var mAdapter: BazaarContentAdapter? = null
    private val mData = ArrayList<BazaarEntity>()
    override fun getViewBinding(): FragmentBazaarContentBinding =
        FragmentBazaarContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BazaarContentViewModel> =
        BazaarContentViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = GridLayoutManager(DataCompat.checkContext(context), 2)
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadBazaarList(
            RequestBazaarEntity(
                mId,
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mViewModel.mLastTime
            )
        )
    }

    override fun initData() {
        mId = arguments?.getString(ARouterConfig.Key.ID) ?: ""
        mAdapter = BazaarContentAdapter(DataCompat.checkContext(context), mData)
        mViewBinding.rvData.adapter = mAdapter
        loadSmartData()
    }

    override fun initEvent() {
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mDataLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
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