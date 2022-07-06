package com.sbnh.my.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.my.adapter.CompoundDetailedListAdapter
import com.sbnh.my.databinding.ActivityCompoundDetailedListBinding
import com.sbnh.my.viewmodel.CompoundDetailedListViewModel

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
    private val mData: ArrayList<CollectionEntity> = ArrayList()
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
    }





    override fun initEvent() {
        mViewBinding.root.setOnClickListener{
            ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_COMPOUND_PAGER)
        }
    }

}