package com.sbnh.home.activity

import android.view.View
import androidx.core.widget.NestedScrollView
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.home.IN_THE_BOOK
import com.sbnh.comm.entity.home.STATUS_ADVANCING
import com.sbnh.comm.entity.home.STATUS_OUT
import com.sbnh.comm.entity.request.RequestCreateOrderEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.CheckLoginClick
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.home.databinding.ActivityCollectionDetailsBinding
import com.sbnh.home.viewmodel.CollectionDetailsViewModel

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
    private var mOtherId: String = ""

    /**
     * 是否展示收藏详情，默认false，展示商品详情
     */
    //  private var isShowCollectionDetails = false
    private var mCollectionOrderType: Int = Contract.PutOrderType.OFFICIAL

    override fun getViewBinding(): ActivityCollectionDetailsBinding =
        ActivityCollectionDetailsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<CollectionDetailsViewModel> =
        CollectionDetailsViewModel::class.java

    override fun initView() {
        GlideCompat.loadWarpImage(
            com.sbnh.comm.R.mipmap.icon_comm_collection_details_background,
            mViewBinding.aivContentBackground
        )
        mViewBinding.clContent.visibility = View.GONE
    }

    override fun initData() {
        mId = intent.getStringExtra(ARouterConfig.Key.ID) ?: ""
        mCollectionOrderType =
            intent.getIntExtra(ARouterConfig.Key.STATUS, Contract.PutOrderType.OFFICIAL)

        /*   mCid = intent.getStringExtra(ARouterConfig.Key.CID) ?: ""
           isShowCollectionDetails = DataCompat.notEmpty(mCid)
           if (isShowCollectionDetails) {
               mViewModel.loadKnapsackCollectionDetails(mId, mCid)
           } else {
               mViewModel.loadCollectionDetails(mId)
           }*/
        when (mCollectionOrderType) {
            Contract.PutOrderType.GIVE -> {
                mOtherId = intent.getStringExtra(ARouterConfig.Key.OTHER_ID) ?: ""
                mViewModel.loadKnapsackCollectionDetails(mId, mOtherId)
            }
            Contract.PutOrderType.BAZAAR_BUY -> {
                mViewModel.loadBazaarCollectionDetails(mId)
            }
            else -> {
                mViewModel.loadCollectionDetails(mId)
            }
        }

    }

    override fun initEvent() {
        mViewBinding.aivBack.setOnClickListener {
            MetaViewCompat.finishActivitySetResult(this)
        }
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
        mViewBinding.nsvParent.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            LogUtils.w("OnScrollChangeListener--", "$scrollX--$scrollY--$oldScrollX--$oldScrollY")
            val scrollAlpha = scrollY.toFloat() / (mViewBinding.aivContentBackground.height / 2)
            mViewBinding.clTitleParent.alpha = scrollAlpha
            mViewBinding.aivBack.alpha = 1f - scrollAlpha
        })

    }

    private fun setPrice(price: Double?) {
        if ((price ?: -1.0) < 0) {
            mViewBinding.includedBottomContent.atvPrice.visibility = View.GONE
        } else {
            mViewBinding.includedBottomContent.atvPrice.visibility = View.VISIBLE
            UICompat.setText(
                mViewBinding.includedBottomContent.atvPrice,
                com.sbnh.comm.R.string.pay_money,
                DataCompat.getBalanceFormat(DataCompat.toString(price))
            )
        }
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCollectionDetailsLiveData.observe(this) {
            mViewBinding.clContent.visibility = View.VISIBLE
            mViewBinding.includedBottomContent.root.visibility = View.VISIBLE
            mEmptyView?.hide()
            GlideCompat.loadWarpImage(it.dynamicGraph, mViewBinding.aivContent)
            GlideCompat.loadImage(it.header, mViewBinding.civUserHead)
            UICompat.setText(
                mViewBinding.atvUserName,
                DataCompat.checkNotNull(
                    it.nickname,
                    DataCompat.getResString(com.sbnh.comm.R.string.app_name)
                )
            )
            UICompat.setText(mViewBinding.atvContractAddress, it.contractAddress)
            UICompat.setText(mViewBinding.atvChainLogo, it.transactionHash)
            UICompat.setText(mViewBinding.atvWork, it.particulars)
            setPrice(it.price)

            if (mCollectionOrderType == Contract.PutOrderType.GIVE) {
                UICompat.setText(
                    mViewBinding.atvCollectionName,
                    "${it.merchandiseName} #${it.tokenId ?: ""}"
                )
                GlideCompat.loadImage(it.collectibleHeader, mViewBinding.civOwnerUserHead)
                UICompat.setText(
                    mViewBinding.atvOwnerName,
                    DataCompat.checkNotNull(
                        it.collectibleNickname,
                        DataCompat.getResString(com.sbnh.comm.R.string.app_name)
                    )
                )
                UICompat.setText(
                    mViewBinding.includedBottomContent.atvSure,
                    DataCompat.getResString(com.sbnh.comm.R.string.give)
                )
                MetaViewCompat.setClickViewEnable(
                    mViewBinding.includedBottomContent.atvSure,
                    true
                )
                mViewBinding.includedBottomContent.atvSure.setOnClickListener(object :
                    DelayedClick() {
                    override fun onDelayedClick(v: View?) {
                        ARoutersActivity.startGiveCollectionActivity(mOtherId, mId)
                    }
                })
                UICompat.setText(
                    mViewBinding.includedLimit.atvLimitTitle,
                    com.sbnh.comm.R.string.serial_number
                )
                UICompat.setText(
                    mViewBinding.includedLimit.atvLimitCount,
                    "#${it.tokenId ?: ""}"
                )
                mViewBinding.includedBottomContent.root.visibility = View.GONE

            } else {
                UICompat.setText(mViewBinding.atvCollectionName, it.merchandiseName)
                GlideCompat.loadImage(it.header, mViewBinding.civOwnerUserHead)
                UICompat.setText(
                    mViewBinding.atvOwnerName,
                    DataCompat.checkNotNull(
                        it.collectibleNickname,
                        DataCompat.getResString(com.sbnh.comm.R.string.app_name)
                    )
                )
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
                    IN_THE_BOOK -> {
                        UICompat.setText(
                            mViewBinding.includedBottomContent.atvSure,
                            DataCompat.getResString(com.sbnh.comm.R.string.in_the_book)
                        )
                        MetaViewCompat.setClickViewEnable(
                            mViewBinding.includedBottomContent.atvSure,
                            false
                        )
                    }
                    else -> {
                        /*UICompat.setText(
                            mViewBinding.includedBottomContent.atvSure,
                            DataCompat.getResString(com.sbnh.comm.R.string.sale)
                        )*/
                        SpanTextHelper.with()
                            .append(DataCompat.getResString(com.sbnh.comm.R.string.sale))
                            .appendLine()
                            .appendDrawable(com.sbnh.comm.R.mipmap.icon_home_sale_time)
                            .append(TimeCompat.getTimeFormat(it.saleTime, "MM.dd HH:mm"))
                            .setSize(12, true)
                            .crete(mViewBinding.includedBottomContent.atvSure)
                        MetaViewCompat.setClickViewEnable(
                            mViewBinding.includedBottomContent.atvSure,
                            false
                        )
                        MetaViewCompat.setClickButton(
                            mViewBinding.includedBottomContent.atvSure,
                            Contract.DP.VALUE_8F
                        )
                    }
                }
                mViewBinding.includedBottomContent.atvSure.setOnClickListener(object :
                    CheckLoginClick() {
                    override fun onCheckLoginClick(v: View?) {
                        /*  val type = when (mCollectionOrderType) {
                              Contract.CollectionDetailsStatus.BAZAAR -> {
                                  Contract.PutOrderType.BAZAAR_BUY
                              }
                              else -> {
                                  Contract.PutOrderType.OFFICIAL
                              }
                          }*/
                        val entity = RequestCreateOrderEntity(it.id, mId, mCollectionOrderType)
                        mViewModel.commitOrder(entity)

                    }

                })
                if (mCollectionOrderType == Contract.PutOrderType.BAZAAR_BUY) {
                    UICompat.setText(
                        mViewBinding.includedLimit.atvLimitTitle,
                        com.sbnh.comm.R.string.serial_number
                    )
                    UICompat.setText(
                        mViewBinding.includedLimit.atvLimitCount,
                        "#${it.tokenId ?: ""}"
                    )
                } else {
                    UICompat.setText(
                        mViewBinding.includedLimit.atvLimitTitle,
                        com.sbnh.comm.R.string.limit_number
                    )
                    UICompat.setText(
                        mViewBinding.includedLimit.atvLimitCount,
                        "${it.remainQuantity}/${it.totalQuantity}"
                    )
                }

            }

        }

        // if (!isShowCollectionDetails) {
        mViewModel.mCommitOrderLiveData.observe(this) {
            val body = BaseEntity.getData(it)
            LogUtils.w("observe--", body?.id + "---")
            ARoutersActivity.startOrderDetailsActivity(body?.id, mCollectionOrderType)
            MetaViewCompat.finishActivity(this)
        }
    }

    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        MetaViewCompat.setStatusBarMargin(mViewBinding.pvTitle,this)
        MetaViewCompat.setStatusBarMargin(mViewBinding.aivBack,this)
    }


}