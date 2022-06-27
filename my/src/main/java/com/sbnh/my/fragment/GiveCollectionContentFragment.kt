package com.sbnh.my.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.ALL
import com.sbnh.comm.entity.my.GiveCollectionEntity
import com.sbnh.comm.entity.request.RequestGiveCollectionListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.my.adapter.GiveCollectionListAdapter
import com.sbnh.my.databinding.FragmentMyOrderContentBinding
import com.sbnh.my.viewmodel.GiveCollectionContentViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

@Route(path = ARouterConfig.Path.My.FRAGMENT_GIVE_COLLECTION_CONTENT)
class GiveCollectionContentFragment :
    BaseCompatFragment<FragmentMyOrderContentBinding, GiveCollectionContentViewModel>() {
    // private val requestEntity = RequestOrderListEntity(mViewModel.mPagerNum,mViewModel.mPagerSize)
    private var mGiveType: Int = ALL
    private val mData = ArrayList<GiveCollectionEntity>()
    private var mAdapter: GiveCollectionListAdapter? = null
    override fun getViewBinding(): FragmentMyOrderContentBinding =
        FragmentMyOrderContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<GiveCollectionContentViewModel> =
        GiveCollectionContentViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        mGiveType = arguments?.getInt(ARouterConfig.Key.ID, ALL)?: ALL
        mAdapter = GiveCollectionListAdapter(requireContext(), mData)
        mViewBinding.rvData.adapter = mAdapter
        mViewModel.loadCachedGiveCollectionPagerEntity(mGiveType)
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mViewModel.giveCollectionList(
            RequestGiveCollectionListEntity(
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mGiveType
            )
        )
    }

    override fun initEvent() {
    }

    override fun isLoadEmptyView(): Boolean = true

    override fun initObserve() {
        super.initObserve()
        mViewModel.mGiveCollectionListLiveData.observe(this) {
            if (mViewModel.isRefresh) {
                mData.clear()
                // 缓存
                mViewModel.cacheGiveCollectionPagerEntity(mGiveType, it)
            }
            val data = BasePagerEntity.getData(it)
            if (CollectionCompat.notEmptyList(data)) {
                mData.addAll(data!!)
            }
            mAdapter?.notifyDataSetChanged()
        }

        mViewModel.mCachedGiveCollectionListLiveData.observe(this) {
            mData.clear()
            val data = BasePagerEntity.getData(it)
            if (CollectionCompat.notEmptyList(data)) {
                mData.addAll(data!!)
                mAdapter?.notifyDataSetChanged()
                mViewModel.mPagerNum++
                mViewBinding.refreshLayout.setEnableLoadMore(true)
            } else {
                loadSmartData()
            }
        }
    }
}