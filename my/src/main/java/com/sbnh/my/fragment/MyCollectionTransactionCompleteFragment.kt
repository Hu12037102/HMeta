package com.sbnh.my.fragment

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestPagerTypeEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.MyCollectionTransactionCompleteAdapter
import com.sbnh.my.databinding.FragmentMyCollectionTransactionCompleteBinding
import com.sbnh.my.viewmodel.MyCollectionTransactionCompleteViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 16:30
 * 更新时间: 2022/7/18 16:30
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION_TRANSACTION_DOWN)
class MyCollectionTransactionCompleteFragment :
    BaseCompatFragment<FragmentMyCollectionTransactionCompleteBinding, MyCollectionTransactionCompleteViewModel>() {
    private var mAdapter: MyCollectionTransactionCompleteAdapter? = null
    private val mData = ArrayList<MyCollectionEntity>()
    private var mType: Int = Contract.UNKNOWN_INT_VALUE
    private var isFirstLoad: Boolean = true
    override fun getViewBinding(): FragmentMyCollectionTransactionCompleteBinding =
        FragmentMyCollectionTransactionCompleteBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyCollectionTransactionCompleteViewModel> =
        MyCollectionTransactionCompleteViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = GridLayoutManager(context, 2)
    }

    override fun initData() {
        mType = arguments?.getInt(ARouterConfig.Key.TYPE) ?: Contract.UNKNOWN_INT_VALUE
        mAdapter = MyCollectionTransactionCompleteAdapter(DataCompat.checkContext(context), mData)
        mViewBinding.rvData.adapter = mAdapter
    }
    override fun onResume() {
        super.onResume()
        mViewModel.loadUserInfo()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadCollectionTransactions(
            RequestPagerTypeEntity(
                mViewModel.mPagerNum,
                mViewModel.mPagerSize,
                mViewModel.mLastTime,
                mType
            )
        )
    }

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        super.resultUserInfo(userInfoEntity)
        if (UserInfoStore.isLogin(userInfoEntity)) {
            mViewBinding.refreshLayout.setEnableRefresh(true)
            if (isFirstLoad) {
                loadSmartData()
                isFirstLoad = false
            }
            mViewBinding.atvLogin.visibility = View.GONE
            UICompat.notifyAdapterUpdateDateChanged(mEmptyLayout, mAdapter, mData)
        } else {
            mData.clear()
            UICompat.notifyAdapterUpdateDateChanged(
                mEmptyLayout,
                mAdapter,
                mData,
            )
            mEmptyLayout?.hide()
            mViewBinding.atvLogin.visibility = View.VISIBLE
            mViewBinding.refreshLayout.setEnableRefresh(false)
        }
    }

    override fun initEvent() {
        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
               /* val entity = mData[position]
                ARoutersActivity.startCollectionDetailsActivity(
                    entity.merchandiseId,
                    entity.id,
                    Contract.PutOrderType.GIVE
                )*/
            }

            override fun longClickItem(view: View, position: Int) {
            }

        })
        mViewBinding.atvLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                startActivityForResult(
                    ARoutersActivity.createLoginIntent(
                        DataCompat.checkContext(
                            context
                        )
                    )
                )
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionTransactionLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            UICompat.notifyAdapterAddDateChanged(
                mEmptyLayout,
                mAdapter,
                mViewModel.isRefresh,
                mData,
                data
            )
        }
    }

    override fun onActivityResultCallback(result: ActivityResult) {
        super.onActivityResultCallback(result)
        if(result.resultCode == Activity.RESULT_OK){
            loadRefreshSmartData()
        }
    }
}