package com.sbnh.my.fragment

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.DialogCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.dialog.TitleDialog
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestPagerTypeEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.MyCollectionTransactionUpAdapter
import com.sbnh.my.databinding.FragmentMyCollectionTransactionUpBinding
import com.sbnh.my.viewmodel.MyCollectionTransactionUpViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/18 15:13
 * 更新时间: 2022/7/18 15:13
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION_TRANSACTION_UP)
class MyCollectionTransactionUpFragment :
    BaseCompatFragment<FragmentMyCollectionTransactionUpBinding, MyCollectionTransactionUpViewModel>() {
    private var mDownCollectionTokenId: Long? = null
    private val mData = ArrayList<MyCollectionEntity>()
    private var mAdapter: MyCollectionTransactionUpAdapter? = null
    private var isFirstLoad: Boolean = true
    private var mType: Int = Contract.UNKNOWN_INT_VALUE
    override fun getViewBinding(): FragmentMyCollectionTransactionUpBinding =
        FragmentMyCollectionTransactionUpBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyCollectionTransactionUpViewModel> =
        MyCollectionTransactionUpViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = GridLayoutManager(DataCompat.checkContext(context), 2)

    }

    override fun initData() {
        mType = arguments?.getInt(ARouterConfig.Key.TYPE, Contract.UNKNOWN_INT_VALUE)
            ?: Contract.UNKNOWN_INT_VALUE
        mAdapter = MyCollectionTransactionUpAdapter(DataCompat.checkContext(context), mData)
        mViewBinding.rvData.adapter = mAdapter

        //
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadUserInfo()
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

    override fun initEvent() {
        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
                val entity = mData[position]
                ARoutersActivity.startCollectionDetailsActivity(
                    entity.merchandiseId,
                    entity.id,
                    Contract.PutOrderType.GIVE
                )
            }

            override fun longClickItem(view: View, position: Int) {
            }

        })
        mAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                val entity = mData[position]
                val titleDialog =
                    TitleDialog(DataCompat.getResString(com.sbnh.comm.R.string.are_you_sure_down_collection))
                DialogCompat.showFragmentDialog(titleDialog, childFragmentManager)
                titleDialog.setOnDialogItemInfoClickListener(object :
                    OnDialogItemInfoClickListener {
                    override fun onClickConfirm(view: View?) {
                        mDownCollectionTokenId = entity.tokenId
                        mViewModel.downCollection(DataCompat.toString(entity.id))
                        titleDialog.dismiss()
                    }

                    override fun onClickCancel(view: View?) {
                        titleDialog.dismiss()
                    }

                })
            }

        })
        mViewBinding.atvLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                //  ARoutersActivity.startLoginActivity()
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
        mViewModel.mDownCollectionLiveData.observe(this) {
            showToast(com.sbnh.comm.R.string.down_collection_succeed)
            val iterator = mData.iterator()
            while (iterator.hasNext()) {
                val entity = iterator.next()
                if (entity.tokenId == mDownCollectionTokenId) {
                    iterator.remove()
                }
            }
            UICompat.notifyAdapterUpdateDateChanged(
                mEmptyLayout,
                mAdapter,
                mData,
            )
            //    mAdapter?.updateDowCollection(mDownCollectionId)
        }
    }

    override fun onActivityResultCallback(result: ActivityResult) {
        super.onActivityResultCallback(result)
        LogUtils.w("onActivityResultCallback", "$result")
        if (result.resultCode == Activity.RESULT_OK) {
            loadRefreshSmartData()
        }
    }

}