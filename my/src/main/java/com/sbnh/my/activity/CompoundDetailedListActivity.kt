package com.sbnh.my.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.CompoundDetailedListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.my.adapter.CompoundDetailedListAdapter
import com.sbnh.my.databinding.ActivityCompoundDetailedListBinding
import com.sbnh.my.viewmodel.CompoundDetailedListViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/6 9:27
 * 更新时间: 2022/7/6 9:27
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_COMPOUND_DETAILED_LIST)
class CompoundDetailedListActivity :
    BaseCompatActivity<ActivityCompoundDetailedListBinding, CompoundDetailedListViewModel>() {
    private var mAdapter: CompoundDetailedListAdapter? = null
    private val mData: ArrayList<CompoundDetailedListEntity> = ArrayList()
    override fun getViewBinding(): ActivityCompoundDetailedListBinding =
        ActivityCompoundDetailedListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<CompoundDetailedListViewModel> =
        CompoundDetailedListViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {
        mAdapter = CompoundDetailedListAdapter(this, mData)
        mViewBinding.rvData.adapter = mAdapter
        loadSmartData()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadCompoundDetailedList()
    }


    override fun initEvent() {

        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
                ARouters.build(ARouterConfig.Path.My.ACTIVITY_COMPOUND_PAGER)
                    .withString(ARouterConfig.Key.ID, mData[position].id)
                    .withString(ARouterConfig.Key.DETAILS_ID,mData[position].synthesisConfigId)
                    .navigation()
            }

            override fun longClickItem(view: View, position: Int) {
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCompoundDetailedListLiveData.observe(this) {
            UICompat.notifyAdapterDateChanged(mEmptyView, mAdapter, mViewModel.isRefresh, mData, it)

        }
    }

    override fun finish() {
        MetaViewCompat.setResultOK(this)
        super.finish()
    }
}