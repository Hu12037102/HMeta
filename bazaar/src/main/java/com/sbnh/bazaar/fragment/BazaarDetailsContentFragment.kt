package com.sbnh.bazaar.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.bazaar.adapter.BazaarDataAdapter
import com.sbnh.bazaar.databinding.FragmentBazaarDetailsContentBinding
import com.sbnh.bazaar.viewmodel.BazaarDetailsContentViewModel
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.bazaar.BazaarDataEntity
import com.sbnh.comm.entity.request.RequestBazaarDataEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 17:13
 * 更新时间: 2022/7/12 17:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_DETAILS_CONTENT)
class BazaarDetailsContentFragment :
    BaseCompatFragment<FragmentBazaarDetailsContentBinding, BazaarDetailsContentViewModel>() {
    private var mId: String = ""
    private var mType: Int = SelectorTabEntity.TYPE_BAZAAR
    private var isUpSort: Boolean = false
    private var mAdapter: BazaarDataAdapter? = null
    private val mData = ArrayList<BazaarDataEntity>()
    override fun getViewBinding(): FragmentBazaarDetailsContentBinding =
        FragmentBazaarDetailsContentBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BazaarDetailsContentViewModel> =
        BazaarDetailsContentViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(DataCompat.checkContext(context))
    }

    override fun initData() {
        mAdapter = BazaarDataAdapter(DataCompat.checkContext(context), mData)
        mViewBinding.rvData.adapter = mAdapter
        arguments?.let {
            mId = DataCompat.toString(it.getString(ARouterConfig.Key.ID))
            mType = NumberCompat.checkInt(
                it.getInt(ARouterConfig.Key.TYPE),
                SelectorTabEntity.TYPE_BAZAAR
            )
            isUpSort = it.getBoolean(ARouterConfig.Key.BOOLEAN_VALUE)
            loadSmartData()
        }


    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadBazaarDetailsList(
            RequestBazaarDataEntity(
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                isUpSort,
                mId,
                mViewModel.mLastTime,
                mType
            )
        )
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

    fun updateSort(isUpSort: Boolean) {
        this.isUpSort = isUpSort
        loadSmartData()
    }

    override fun initEvent() {
        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
                ARoutersActivity.startCollectionDetailsActivity(mData[position].marketSecondaryCategoryId)
            }

            override fun longClickItem(view: View, position: Int) {
            }

        })
    }
}