package com.sbnh.my.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.dialog.CompoundCollectionPreviewDialog
import com.sbnh.comm.entity.my.CompoundPagerEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.CompoundPagerAdapter
import com.sbnh.my.databinding.ActivityCompoundPagerBinding
import com.sbnh.my.viewmodel.CompoundPagerViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/6 11:28
 * 更新时间: 2022/7/6 11:28
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_COMPOUND_PAGER)
class CompoundPagerActivity :
    BaseCompatActivity<ActivityCompoundPagerBinding, CompoundPagerViewModel>() {
    private var mId: String = ""
    private var mDetailsId: String = ""
    private var mAdapter: CompoundPagerAdapter? = null
    private val mData = ArrayList<CompoundPagerEntity.Details>()
    override fun getViewBinding(): ActivityCompoundPagerBinding =
        ActivityCompoundPagerBinding.inflate(layoutInflater)

    private var mAfterData: ArrayList<CompoundPagerEntity.Details> = ArrayList()
    override fun getViewModelClass(): Class<CompoundPagerViewModel> =
        CompoundPagerViewModel::class.java

    override fun initView() {
        GlideCompat.loadImage(
            com.sbnh.comm.R.mipmap.icon_my_compound_pager_background,
            mViewBinding.aivBackground
        )
        GlideCompat.loadImage(
            com.sbnh.comm.R.mipmap.icon_my_compound_pager_content,
            mViewBinding.aivContent
        )
        mViewBinding.rvData.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        /* val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL)
         val drawable = GradientDrawableCompat.create()
         drawable.setSize(PhoneCompat.dp2px(this, 8f), ViewGroup.LayoutParams.MATCH_PARENT)
         drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorTransparent))
         dividerItemDecoration.setDrawable(drawable)
         mViewBinding.rvData.addItemDecoration(dividerItemDecoration)*/
        initAnimation()

    }

    private fun initAnimation() {
        val scaleX = ObjectAnimator.ofFloat(
            mViewBinding.aivStart,
            "scaleX",
            1.0f,
            0.98f,
            0.96f,
            0.94f,
            0.92f,
            0.90f,
            0.92f,
            0.94f,
            0.96f,
            0.98f,
            1.0f
        )
        scaleX.repeatCount = ValueAnimator.INFINITE
        val scaleY = ObjectAnimator.ofFloat(
            mViewBinding.aivStart,
            "scaleY",
            1.0f,
            0.98f,
            0.96f,
            0.94f,
            0.92f,
            0.90f,
            0.92f,
            0.94f,
            0.96f,
            0.98f,
            1.0f
        )
        scaleY.repeatCount = ValueAnimator.INFINITE
        val set = AnimatorSet()
        set.duration = 1500
        set.interpolator = AccelerateDecelerateInterpolator()
        set.playTogether(scaleX, scaleY)
        set.start()
    }

    override fun initData() {
        mViewBinding.atvCompoundCondition.requestFocus()
        mId = DataCompat.toString(intent.getStringExtra(ARouterConfig.Key.ID))
        mDetailsId = DataCompat.toString(intent.getStringExtra(ARouterConfig.Key.DETAILS_ID))
        mAdapter = CompoundPagerAdapter(this, mData)
        mViewBinding.rvData.adapter = mAdapter
        loadSmartData()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadCompoundPagerDetails(mDetailsId)
    }

    override fun initEvent() {

    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCompoundPagerLiveData.observe(this) {
            val data = it.synthesisMerchandiseBO
            val afterData = it.afterSynthesis
            UICompat.notifyAdapterAddDateChanged(null, mAdapter, true, mData, data)
            if (CollectionCompat.notEmptyList(afterData)) {
                mAfterData.clear()
                CollectionCompat.addAll(mAfterData, afterData)
                val sb = StringBuilder()
                val afterEntity = afterData?.get(0)
                for (entity in mData) {
                    sb.append(
                        "${entity.merchandiseName}*${entity.count} ${
                            if (mData.lastIndex == mData.indexOf(
                                    entity
                                )
                            ) "= ${afterEntity?.merchandiseName}*${afterEntity?.count}" else "+ "
                        }"
                    )
                }
                UICompat.setText(
                    mViewBinding.atvCompoundCondition,
                    com.sbnh.comm.R.string.collection_as_required_s,
                    sb
                )
            }
            mViewBinding.aivStart.setOnClickListener(object : DelayedClick() {
                override fun onDelayedClick(v: View?) {
                    if (isCanCompound()) {
                        mViewModel.compoundCollection(DataCompat.toString(mId))
                    } else {
                        showToast(com.sbnh.comm.R.string.the_raw_materials_needed_are_not_enough)
                    }
                }

            })

        }
        mViewModel.mCompoundCollectionLiveData.observe(this) {
            if (CollectionCompat.notEmptyList(mAfterData)) {
                val entity = mAfterData[0]
                val dialog = CompoundCollectionPreviewDialog.create(
                    DataCompat.toString(entity.resourceUrl),
                    DataCompat.toString(entity.merchandiseName)
                )
                DialogCompat.showFragmentDialog(dialog, supportFragmentManager)

                for (beforeEntity in mData) {
                    beforeEntity.availableCount =
                        NumberCompat.numberSub(beforeEntity.availableCount, beforeEntity.count)
                }
                mAdapter?.notifyDataSetChanged()

            }


        }
    }

    private fun isCanCompound(): Boolean {
        var count = 0
        for (entity in mData) {
            if ((entity.availableCount ?: 0) >= (entity.count ?: 0)) {
                count++
            }
        }
        return count == (CollectionCompat.getListSize(mData))
    }

    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        MetaViewCompat.setStatusBarMargin(mViewBinding.pvTitle, this)
    }
}