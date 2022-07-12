package com.sbnh.bazaar.dialog

import android.graphics.drawable.Drawable
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.adapter.BazaarDetailsTabAdapter
import com.sbnh.bazaar.databinding.DialogBazaarDetailsBinding
import com.sbnh.bazaar.viewmodel.BazaarDetailsViewModel
import com.sbnh.comm.Contract
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.bazaar.BazaarEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 15:26
 * 更新时间: 2022/7/12 15:26
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.DIALOG_BAZAAR_DETAILS)
class BazaarDetailsDialog : BaseDataDialog<DialogBazaarDetailsBinding, BazaarDetailsViewModel>() {
    override fun getViewBinding(): DialogBazaarDetailsBinding =
        DialogBazaarDetailsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BazaarDetailsViewModel> =
        BazaarDetailsViewModel::class.java

    private val mTabData = ArrayList<SelectorTabEntity>()
    private var mTabAdapter: BazaarDetailsTabAdapter? = null
    override fun initView() {
        ViewCompat.setBackground(mViewBinding.clParent, createParentBackground())
        ViewCompat.setBackground(mViewBinding.atvSellTitle, createCountTitleDrawable())
        ViewCompat.setBackground(mViewBinding.atvSellCount, createCountDrawable())
        ViewCompat.setBackground(mViewBinding.atvCirculateTitle, createCountTitleDrawable())
        ViewCompat.setBackground(mViewBinding.atvCirculateCount, createCountDrawable())
        mViewBinding.rvTab.layoutManager = LinearLayoutManager(
            DataCompat.checkContext(context),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun initData() {
        arguments?.getParcelable<BazaarEntity>(ARouterConfig.Key.PARCELABLE)?.apply {
            GlideCompat.loadWarpImage(this.resourceUrl, mViewBinding.aivContent)
            UICompat.setText(mViewBinding.atvCollectionName, this.merchandiseName)
            GlideCompat.loadImage(this.header, mViewBinding.civHead)
            UICompat.setText(mViewBinding.atvUserName, this.nickName)
            UICompat.setText(
                mViewBinding.atvSellCount,
                com.sbnh.comm.R.string.part_s,
                "${NumberCompat.intValue(this.totalQuantity)}"
            )
            UICompat.setText(
                mViewBinding.atvCirculateCount,
                com.sbnh.comm.R.string.part_s,
                "${NumberCompat.intValue(this.circulationNum)}"
            )

        }
        initTab()

    }

    private fun initTab() {
        mTabData.addAll(mViewModel.getTabs())
        mTabAdapter = BazaarDetailsTabAdapter(DataCompat.checkContext(context), mTabData)
        mViewBinding.rvTab.adapter = mTabAdapter

    }

    override fun initEvent() {
        mViewBinding.aivClose.setOnClickListener { dismiss() }
        mViewBinding.viewFinish.setOnClickListener { dismiss() }
    }

    private fun createParentBackground(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.cornerRadii = floatArrayOf(
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_12F).toFloat(),
            0f, 0f, 0f, 0f

        )
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF101010))
        return drawable
    }

    private fun createCountTitleDrawable(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF717171))
        drawable.cornerRadii = floatArrayOf(
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            0f,
            0f,
            0f,
            0f,
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat()
        )
        return drawable
    }

    private fun createCountDrawable(): Drawable {
        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF2D2D2D))
        drawable.cornerRadii = floatArrayOf(
            0f,
            0f,
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            PhoneCompat.dp2px(DataCompat.checkContext(context), Contract.DP.VALUE_2F).toFloat(),
            0f,
            0f
        )
        return drawable
    }
}