package com.sbnh.home.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.ToastCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.home.adapter.HomeCollectionListAdapter
import com.sbnh.home.databinding.FragmentHomeBinding
import com.sbnh.home.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:13
 * 更新时间: 2022/6/13 16:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.Home.FRAGMENT_HOME)
class HomeFragment : BaseCompatFragment<FragmentHomeBinding, HomeViewModel>() {
    //  private val mRequestCollectionEntity = RequestPagerListEntity()
    private val mCollectionData = ArrayList<CollectionEntity>()
    private var mCollectionAdapter: HomeCollectionListAdapter? = null
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        mCollectionAdapter = HomeCollectionListAdapter(requireContext(), mCollectionData)
        mViewBinding.rvData.adapter = mCollectionAdapter
        // mViewModel.loadCollectionList(mRequestCollectionEntity)
        onLoadSmartData()
    }

    override fun isLoadEmptyView(): Boolean = true
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
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionData.addAll(data!!)
                mCollectionAdapter?.notifyDataSetChanged()
            }
        }

    }


}