package com.sbnh.home.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.compat.WebViewCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.home.HomeBannerEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.smart.SmartRefreshLayoutCompat
import com.sbnh.home.adapter.HomeBannerAdapter
import com.sbnh.home.adapter.HomeCollectionListAdapter
import com.sbnh.home.databinding.FragmentHomeBinding
import com.sbnh.home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:13
 * 更新时间: 2022/6/13 16:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.Home.FRAGMENT_HOME)
class HomeFragment : BaseCompatFragment<FragmentHomeBinding, HomeViewModel>() {
    //  private val mRequestCollectionEntity = RequestPagerListEntity()
    private var mBannerAdapter: HomeBannerAdapter? = null
    private val mBannerData = ArrayList<HomeBannerEntity>()
    private val mCollectionData = ArrayList<CollectionEntity>()
    private var mCollectionAdapter: HomeCollectionListAdapter? = null
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(context)
        SmartRefreshLayoutCompat.wipeDamp(mViewBinding.refreshLayout)
    }

    override fun initData() {
        initBanner()
        mCollectionAdapter = HomeCollectionListAdapter(requireContext(), mCollectionData)
        mViewBinding.rvData.adapter = mCollectionAdapter
        loadSmartData()
    }

    private fun initBanner() {
        mBannerAdapter = HomeBannerAdapter(requireContext(), mBannerData)
        mViewBinding.banner
            .addBannerLifecycleObserver(this)
            .setAdapter(mBannerAdapter)
            .setOnBannerListener { _, position ->
                val entity = mBannerData[position]
                if (entity.skipType == WebViewCompat.SKIP_TYPE_IN) {
                    ARoutersActivity.startWebContentActivity(entity.skipUrl)
                } else if (entity.skipType == WebViewCompat.SKIP_TYPE_OUT) {
                    ARoutersActivity.startBrowserActivity(context, entity.skipUrl)
                }
            }
    }

    override fun initEvent() {
        mEmptyLayout?.setOnClickListener {

        }

        mCollectionAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                ARoutersActivity.startCollectionDetailsActivity(mCollectionData[position].id)
            }

        })
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        if (isRefresh) {
            mViewModel.loadBanner()
        }
        mViewModel.loadCollectionList(
            RequestPagerListEntity(
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mViewModel.mLastTimestamp
            )
        )
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            /* if (mViewModel.isRefresh) {
                 mCollectionData.clear()
             }
             if (CollectionCompat.notEmptyList(data)) {
                 mCollectionData.addAll(data!!)
             }

             mCollectionAdapter?.notifyDataSetChanged()*/
            UICompat.notifyAdapterDateChanged(
                mEmptyLayout,
                mCollectionAdapter,
                mViewModel.isRefresh,
                mCollectionData,
                data
            )
        }

        mViewModel.mBannerLiveData.observe(this) {
            mBannerData.clear()
            if (CollectionCompat.notEmptyList(it)) {
                mBannerData.addAll(it!!)
            }
            mBannerAdapter?.notifyDataSetChanged()
        }
    }

    override fun resultPublicData(it: Int) {
        super.resultPublicData(it)
        if (it == BaseViewModel.STATUE_HTTP_ERROR) {
            mEmptyLayout?.show()
        }
    }

}