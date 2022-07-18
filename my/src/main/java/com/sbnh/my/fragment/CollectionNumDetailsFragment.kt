package com.sbnh.my.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.DialogCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.dialog.SetTransactionDialog
import com.sbnh.comm.dialog.TitleDialog
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.my.CollectionNumDetailsEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestCollectionNumDetailsEntity
import com.sbnh.comm.entity.request.RequestUpCollectionEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
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
    private var mRequestUpCollectionEntity: RequestUpCollectionEntity = RequestUpCollectionEntity()
    private var mDownCollectionId=""

    override fun getViewBinding(): FragmentCollectionNumDetailsBinding =
        FragmentCollectionNumDetailsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<CollectionNumDetailsViewModel> =
        CollectionNumDetailsViewModel::class.java

    override fun initView() {
        mMyCollection = arguments?.let {
            val myCollection: MyCollectionEntity? =
                it.getParcelable(ARouterConfig.Key.MY_COLLECTION)
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
        context?.let {
            mViewBinding.rvData.adapter = CollectionNumDetailsListAdapter(
                it,
                mCollectionNumDetailsListData
            ).also { adapter -> mCollectionNumDetailsListAdapter = adapter }
        }
        mMyCollection?.run {
            //   mViewModel.loadCachedCollectionNumDetailsPagerEntity(merchandiseId?: "")
            loadSmartData()
        }
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
                val entity = mCollectionNumDetailsListData[position]
                ARoutersActivity.startCollectionDetailsActivity(
                    entity.merchandiseId,
                    entity.id,
                    Contract.PutOrderType.GIVE
                )
                activity?.finish()
            }

            override fun longClickItem(view: View, position: Int) {
//                TODO("Not yet implemented")
            }

        })
        mCollectionNumDetailsListAdapter?.setOnRecyclerItemClickListener(object :
            OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                val entity = mCollectionNumDetailsListData[position]
                when (entity.status) {
                    Contract.MyCollectionStatus.NORMAL -> {
                        val dialog =
                            ARouters.getFragment(ARouterConfig.Path.Comm.DIALOG_SET_TRANSACTION)
                        if (dialog is SetTransactionDialog) {
                            DialogCompat.showFragmentDialog(dialog, childFragmentManager)
                            dialog.setOnCallbackValues(object : BaseDataDialog.OnCallbackValues {
                                override fun onValue(obj: Any) {
                                    if (obj is Double) {
                                        // showToast(com.sbnh.comm.R.string.ready_for_sales)
                                        mRequestUpCollectionEntity.price = obj
                                        mRequestUpCollectionEntity.userCollectionId = entity.id
                                        mViewModel.upCollection(mRequestUpCollectionEntity)

                                    }


                                    dialog.dismiss()
                                }

                            })
                        }

                    }
                    Contract.MyCollectionStatus.SALE -> {
                        val titleDialog =
                            TitleDialog(DataCompat.getResString(com.sbnh.comm.R.string.are_you_sure_down_collection))
                        DialogCompat.showFragmentDialog(titleDialog, childFragmentManager)
                        titleDialog.setOnDialogItemInfoClickListener(object :
                            OnDialogItemInfoClickListener {
                            override fun onClickConfirm(view: View?) {
                                mDownCollectionId= DataCompat.toString(entity.id)
                                mViewModel.downCollection(mDownCollectionId)
                                titleDialog.dismiss()
                            }

                        })
                    }
                }
            }

        })

    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mMyCollection?.run {
            mViewModel.loadCollectionNumDetailsList(
                RequestCollectionNumDetailsEntity(
                    id ?: "",
                    merchandiseId ?: "",
                    time = mViewModel.mLastTime
                )
            )
        }
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionNumDetailsLiveData.observe(this) {
            if (mViewModel.isRefresh) {
                mCollectionNumDetailsListData.clear()
                // 缓存
                mViewModel.cacheCollectionNumDetailsPagerEntity(
                    mMyCollection?.merchandiseId ?: "",
                    it
                )
            }
            val data = BasePagerEntity.getData(it)
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionNumDetailsListData.addAll(data!!)
            }
            mCollectionNumDetailsListAdapter?.notifyDataSetChanged()
        }

        mViewModel.mCachedCollectionNumDetailsLiveData.observe(this) {
            mCollectionNumDetailsListData.clear()
            val data = BasePagerEntity.getData(it)
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionNumDetailsListData.addAll(data!!)
                mCollectionNumDetailsListAdapter?.notifyDataSetChanged()
                it?.lastTime?.let { lastTime ->
                    mViewModel.mLastTime = lastTime
                }
                mViewBinding.refreshLayout.setEnableLoadMore(true)
            } else {
                loadSmartData()
            }
        }
        mViewModel.mUpCollectionLiveData.observe(this) {
            showToast(com.sbnh.comm.R.string.ready_for_sales)
            mCollectionNumDetailsListAdapter?.updateUpCollection(
                mRequestUpCollectionEntity.userCollectionId,
                mRequestUpCollectionEntity.price
            )
        }
        mViewModel.mDownCollectionLiveData.observe(this){
            showToast(com.sbnh.comm.R.string.down_collection_succeed)
            mCollectionNumDetailsListAdapter?.updateDowCollection(mDownCollectionId)
        }
    }

}