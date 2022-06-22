package com.sbnh.home.activity

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.home.STATUS_ADVANCING
import com.sbnh.comm.entity.home.STATUS_OUT
import com.sbnh.comm.entity.request.RequestCreateOrderEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.CheckLoginClick
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.home.databinding.ActivityCollectionDetailsBinding
import com.sbnh.home.viewmodel.CollectionDetailsViewModel
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/20 9:34
 * 更新时间: 2022/6/20 9:34
 * 描述:
 */
@Route(path = ARouterConfig.Path.Home.ACTIVITY_COLLECTION_DETAILS)
class CollectionDetailsActivity :
    BaseCompatActivity<ActivityCollectionDetailsBinding, CollectionDetailsViewModel>() {
    private var mId: String = ""
    override fun getViewBinding(): ActivityCollectionDetailsBinding =
        ActivityCollectionDetailsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<CollectionDetailsViewModel> =
        CollectionDetailsViewModel::class.java

    override fun initView() {
        GlideCompat.loadWarpImage(
            com.sbnh.comm.R.mipmap.icon_comm_collection_details_background,
            mViewBinding.aivContentBackground
        )
    }

    override fun initData() {
        mId = intent.getStringExtra(ARouterConfig.Key.ID) ?: ""
        mViewModel.loadCollectionDetails(mId)
    }

    override fun initEvent() {
        mViewBinding.aivContractAddressCopy.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                PhoneCompat.copyText(MetaViewCompat.getTextViewText(mViewBinding.atvContractAddress))
                showToast(com.sbnh.comm.R.string.copy_succeed)
            }
        })
        mViewBinding.aivChainLogoCopy.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                PhoneCompat.copyText(MetaViewCompat.getTextViewText(mViewBinding.atvChainLogo))
                showToast(com.sbnh.comm.R.string.copy_succeed)
            }

        })

    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionDetailsLiveData.observe(this) {
            GlideCompat.loadWarpImage(it.dynamicGraph, mViewBinding.aivContent)
            UICompat.setText(mViewBinding.atvCollectionName, it.collectibleNickname)
            UICompat.setText(
                mViewBinding.atvCollectionNumber,
                if (DataCompat.isNull(it.tokenId)) "" else "#${it.tokenId} "
            )
            GlideCompat.loadImage(it.header, mViewBinding.civUserHead)
            UICompat.setText(mViewBinding.atvUserName, it.nickname)
            UICompat.setText(
                mViewBinding.includedLimit.atvLimitCount,
                "${it.remainQuantity}/${it.totalQuantity}"
            )
            UICompat.setText(mViewBinding.atvOwnerName, it.nickname)
            UICompat.setText(mViewBinding.atvContractAddress, it.contractAddress)
            UICompat.setText(mViewBinding.atvChainLogo, it.transactionHash)
            UICompat.setText(mViewBinding.atvWork, it.particulars)
            UICompat.setText(mViewBinding.includedBottomContent.atvPrice, "￥${it.price}")
            when (it.saleStatus) {
                STATUS_ADVANCING -> {
                    UICompat.setText(
                        mViewBinding.includedBottomContent.atvSure,
                        DataCompat.getResString(com.sbnh.comm.R.string.buy)
                    )
                    MetaViewCompat.setClickViewEnable(
                        mViewBinding.includedBottomContent.atvSure,
                        true
                    )
                }
                STATUS_OUT -> {
                    UICompat.setText(
                        mViewBinding.includedBottomContent.atvSure,
                        DataCompat.getResString(com.sbnh.comm.R.string.sold_out)
                    )
                    MetaViewCompat.setClickViewEnable(
                        mViewBinding.includedBottomContent.atvSure,
                        false
                    )
                }
                else -> {
                    UICompat.setText(
                        mViewBinding.includedBottomContent.atvSure,
                        DataCompat.getResString(com.sbnh.comm.R.string.sale)
                    )
                    MetaViewCompat.setClickViewEnable(
                        mViewBinding.includedBottomContent.atvSure,
                        true
                    )
                }
            }
            mViewBinding.includedBottomContent.atvSure.setOnClickListener(object :
                CheckLoginClick() {
                override fun onCheckLoginClick(v: View?) {
                    lifecycleScope.launch {
                        val userId = UserInfoStore.get().getId()
                        val entity = RequestCreateOrderEntity(it.id, userId)
                        mViewModel.commitOrder(entity)
                    }

                }

            })
        }
        mViewModel.mCommitOrderLiveData.observe(this) {
            val body = BaseEntity.getData(it)
            LogUtils.w("observe--", body?.id + "---")
            ARoutersActivity.startOrderDetailsActivity(body?.id)
        }
    }
}