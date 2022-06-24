package com.sbnh.home.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.WebViewCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.home.HomeBannerEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.smart.SmartRefreshLayoutCompat
import com.sbnh.home.adapter.HomeBannerAdapter
import com.sbnh.home.adapter.HomeCollectionListAdapter
import com.sbnh.home.databinding.FragmentHomeBinding
import com.sbnh.home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.youth.banner.listener.OnBannerListener

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
        onLoadSmartData()
    }

    private fun initBanner() {
        mBannerAdapter = HomeBannerAdapter(requireContext(), mBannerData)
        mViewBinding.banner
            .addBannerLifecycleObserver(this)
            .setAdapter(mBannerAdapter)
            .setOnBannerListener(object : OnBannerListener<HomeBannerEntity> {
                override fun OnBannerClick(data: HomeBannerEntity?, position: Int) {
                    data?.let {
                        if (it.skipType == WebViewCompat.SKIP_TYPE_IN) {
                            ARoutersActivity.startWebContentActivity(it.skipUrl)
                        } else if (it.skipType == WebViewCompat.SKIP_TYPE_OUT) {
                            ARoutersActivity.startBrowserActivity(context, it.skipUrl)
                        }
                    }


                }

            })
    }

    override fun isLoadEmptyView(): Boolean = false
    override fun initEvent() {
        mEmptyLayout?.setOnClickListener {

        }
        mCollectionAdapter?.setOnRecyclerViewItemClickListener(object :
            OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
                TODO("Not yet implemented")
            }

            override fun clickItem(view: View, position: Int) {
                ARoutersActivity.startCollectionDetailsActivity(mCollectionData[position].id)
            }

            override fun longClickItem(view: View, position: Int) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onLoadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        if (isRefresh) {
            mViewModel.loadBanner()
        }
        mViewModel.loadCollectionList(
            RequestPagerListEntity(
                mViewModel.mPagerSize,
                mViewModel.mLastTimestamp
            )
        )
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            if (mViewModel.isRefresh) {
                mCollectionData.clear()
            }
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionData.addAll(data!!)
            }
            mCollectionAdapter?.notifyDataSetChanged()
        }

        mViewModel.mBannerLiveData.observe(this) {
            mBannerData.clear()
            if (CollectionCompat.notEmptyList(it)) {
                mBannerData.addAll(it!!)
            }
            mBannerAdapter?.notifyDataSetChanged()
        }
    }


}