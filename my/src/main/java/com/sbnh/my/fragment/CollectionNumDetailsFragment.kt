package com.sbnh.my.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestCollectionNumDetailsEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.adapter.CollectionNumDetailsListAdapter
import com.sbnh.my.databinding.FragmentCollectionNumDetailsBinding
import com.sbnh.my.viewmodel.CollectionNumDetailsViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

@Route(path = ARouterConfig.Path.My.FRAGMENT_COLLECTION_NUM_DETAILS)
class CollectionNumDetailsFragment :
    BaseCompatFragment<FragmentCollectionNumDetailsBinding, CollectionNumDetailsViewModel>() {

    private var mMyCollection: MyCollectionEntity? = null
    private var mCollectionNumDetailsListAdapter: CollectionNumDetailsListAdapter? = null
    private val mCollectionNumDetailsListData = ArrayList<CollectionNumDetailsEntity>()

    override fun getViewBinding(): FragmentCollectionNumDetailsBinding =
        FragmentCollectionNumDetailsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<CollectionNumDetailsViewModel> =
        CollectionNumDetailsViewModel::class.java

    override fun initView() {
        mMyCollection = arguments?.let {
            val myCollection: MyCollectionEntity? = it.getParcelable(ARouterConfig.Key.MY_COLLECTION)
            myCollection
        }?.apply {
            GlideCompat.loadWarpImage(resourceUrl, mViewBinding.aivContent)
            UICompat.setText(mViewBinding.atvCollectionName, merchandiseName)
            UICompat.setText(mViewBinding.atvCount, "拥有数：${count}")
            UICompat.setText(mViewBinding.atvNickname, nickname)
            GlideCompat.loadImage(header, mViewBinding.civHeader)
        }
    }

    override fun initData() {
        context?.let { mViewBinding.rvData.adapter = CollectionNumDetailsListAdapter(it, mCollectionNumDetailsListData).also { adapter -> mCollectionNumDetailsListAdapter = adapter } }
        mViewBinding.refreshLayout.autoRefresh()
    }

    override fun initEvent() {
        mViewBinding.aivClose.setOnClickListener {
            activity?.run {
                finish()
            }
        }

        mCollectionNumDetailsListAdapter?.setOnRecyclerViewItemClickListener(object :
            OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
                TODO("Not yet implemented")
            }

            override fun clickItem(view: View, position: Int) {
//                ARoutersActivity.startCollectionNumDetailsActivity(mCollectionData[position])
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION)
            }

            override fun longClickItem(view: View, position: Int) {
//                TODO("Not yet implemented")
            }

        })

    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mMyCollection?.run {
            mViewModel.loadCollectionNumDetailsList(RequestCollectionNumDetailsEntity(id?: "", merchandiseId?: "", time = mViewModel.mLastTime))
        }
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionNumDetailsLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionNumDetailsListData.clear()
                mCollectionNumDetailsListData.addAll(data!!)
            }
            mCollectionNumDetailsListAdapter?.notifyDataSetChanged()
        }
    }

}