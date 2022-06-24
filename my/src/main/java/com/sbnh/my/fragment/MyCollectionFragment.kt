package com.sbnh.my.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.home.CollectionEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.MyCollectionListAdapter
import com.sbnh.my.databinding.FragmentCollectionBinding
import com.sbnh.my.viewmodel.MyCollectionViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 16:57
 * 更新时间: 2022/6/14 16:57
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION)
class MyCollectionFragment :
    BaseCompatFragment<FragmentCollectionBinding, MyCollectionViewModel>() {

    private var mCollectionAdapter: MyCollectionListAdapter? = null
    private val mCollectionData = ArrayList<MyCollectionEntity>()

    override fun getViewBinding(): FragmentCollectionBinding =
        FragmentCollectionBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyCollectionViewModel> =
        MyCollectionViewModel::class.java

    override fun initView() {
        context?.let {
            mViewBinding.rvData.layoutManager =
                GridLayoutManager(it, 2)
        }

        MetaViewCompat.setClickButton(mViewBinding.atvLogin, Contract.DP.VALUE_8F)
        mEmptyLayout?.setText(DataCompat.getResString(com.sbnh.comm.R.string.no_collection))
    }

    override fun initData() {
        context?.let { mViewBinding.rvData.adapter = MyCollectionListAdapter(it, mCollectionData).also { adapter -> mCollectionAdapter = adapter } }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadUserInfo()
    }

    override fun initEvent() {
        mViewBinding.atvLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARoutersActivity.startLoginActivity()
            }

        })
        mCollectionAdapter?.setOnRecyclerViewItemClickListener(object :
            OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
                TODO("Not yet implemented")
            }

            override fun clickItem(view: View, position: Int) {
                ARoutersActivity.startCollectionDetailsActivity(mCollectionData[position].id)
            }

            override fun longClickItem(view: View, position: Int) {
//                TODO("Not yet implemented")
            }

        })

//        mEmptyLayout?.setOnClickListener(object : DelayedClick() {
//            override fun onDelayedClick(v: View?) {
//                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION)
//
//            }
//
//        })
    }

    override fun isLoadEmptyView(): Boolean = true

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        if (!UserInfoStore.isLogin(userInfoEntity)) {
            mViewBinding.atvLogin.visibility = View.VISIBLE
            mViewBinding.refreshLayout.setEnableRefresh(false)
            mEmptyLayout?.visibility = View.GONE
        } else {
            mViewBinding.atvLogin.visibility = View.GONE
            mViewBinding.refreshLayout.setEnableRefresh(true)
            mEmptyLayout?.visibility = View.VISIBLE
            onLoadSmartData()
        }
    }

    override fun onLoadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mViewModel.loadCollectionList(RequestPagerListEntity(mViewModel.mPagerSize,mViewModel.mLastTimestamp))
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            mCollectionData.clear()
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionData.addAll(data!!)
                mEmptyLayout?.visibility = View.GONE
            } else {
                mEmptyLayout?.visibility = View.VISIBLE
            }
            mCollectionAdapter?.notifyDataSetChanged()
        }
    }

    override fun isLoadLoadingView(): Boolean = false

}