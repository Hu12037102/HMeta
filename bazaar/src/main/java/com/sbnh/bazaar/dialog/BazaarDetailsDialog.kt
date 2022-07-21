package com.sbnh.bazaar.dialog

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.adapter.BazaarDetailsTabAdapter
import com.sbnh.bazaar.databinding.DialogBazaarDetailsBinding
import com.sbnh.bazaar.fragment.BazaarDetailsContentFragment
import com.sbnh.bazaar.viewmodel.BazaarDetailsViewModel
import com.sbnh.comm.Contract
import com.sbnh.comm.base.callback.OnFragmentResultCallback
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.bazaar.BazaarEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.DelayedClick

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/12 15:26
 * 更新时间: 2022/7/12 15:26
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.DIALOG_BAZAAR_DETAILS)
class BazaarDetailsDialog : BaseDataDialog<DialogBazaarDetailsBinding, BazaarDetailsViewModel>() {
    private var isDownSort: Boolean = false
    private val mFragments = ArrayList<BazaarDetailsContentFragment>()
    private var mIntentEntity: BazaarEntity? = null
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
        ViewCompat.setBackground(mViewBinding.atvBazaarTitle, createCountTitleDrawable())
        ViewCompat.setBackground(mViewBinding.atvBazaarCount, createCountDrawable())
        mViewBinding.rvTab.layoutManager = LinearLayoutManager(
            DataCompat.checkContext(context),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mViewBinding.vpContent.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        UICompat.setText(
            mViewBinding.atvBazaarCount,
            com.sbnh.comm.R.string.part_s,
            Contract.MIN_INT_VALUE
        )
    }

    override fun initData() {
        mIntentEntity =
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
        initPager()

    }

    private fun initPager() {
        for (i in 0 until mTabData.size) {
            LogUtils.w("initPager--", "${mTabData[i]}")
            val fragment = ARouters.build(ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_DETAILS_CONTENT)
                .withString(ARouterConfig.Key.ID, mIntentEntity?.id)
                .withInt(ARouterConfig.Key.TYPE, NumberCompat.checkInt(mTabData[i].type))
                .withBoolean(ARouterConfig.Key.BOOLEAN_VALUE, isDownSort)
                .navigation()
            if (fragment is BazaarDetailsContentFragment) {
                fragment.setOnFragmentResultCallback(object : OnFragmentResultCallback {
                    override fun onCallback(any: Any?) {
                        if (any is Int) {
                            UICompat.setText(
                                mViewBinding.atvBazaarCount,
                                com.sbnh.comm.R.string.part_s,
                                any
                            )
                        } else {
                            dismiss()
                        }

                    }
                })
                mFragments.add(fragment)
            }
        }
        mViewBinding.vpContent.offscreenPageLimit = mTabData.size
        mViewBinding.vpContent.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = mFragments.size
            override fun createFragment(position: Int): Fragment = mFragments[position]

        }

    }

    private val mViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mTabAdapter?.selectorTab(position)
        }
    }

    override fun initEvent() {
        mViewBinding.aivClose.setOnClickListener { dismiss() }
        mViewBinding.viewFinish.setOnClickListener { dismiss() }
        mViewBinding.atvPriceSort.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                isDownSort = !isDownSort
                mViewBinding.atvPriceSort.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    if (isDownSort) com.sbnh.comm.R.mipmap.icon_comm_down_sort else com.sbnh.comm.R.mipmap.icon_comm_up_sort,
                    0
                )
                mFragments[mViewBinding.vpContent.currentItem].updateSort(isDownSort)
            }

        })
        mViewBinding.vpContent.registerOnPageChangeCallback(mViewPagerCallback)
        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                mViewBinding.vpContent.currentItem = position
            }

        })
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

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.vpContent.unregisterOnPageChangeCallback(mViewPagerCallback)
    }
}