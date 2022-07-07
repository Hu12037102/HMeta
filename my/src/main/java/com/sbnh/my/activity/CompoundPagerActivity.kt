package com.sbnh.my.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.animation.AnimatorSetCompat
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.my.CompoundPagerEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
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
    private var mAdapter: CompoundPagerAdapter? = null
    private val mData = ArrayList<CompoundPagerEntity.Details>()
    override fun getViewBinding(): ActivityCompoundPagerBinding =
        ActivityCompoundPagerBinding.inflate(layoutInflater)

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
        UICompat.setText(mViewBinding.atvCompoundCondition,"按考生高考临近安康时间分厘卡机开发萨科圣诞卡艰苦拉萨大家奥兰多")
        mId = DataCompat.toString(intent.getStringExtra(ARouterConfig.Key.ID))
        mAdapter = CompoundPagerAdapter(this, mData)
        mViewBinding.rvData.adapter = mAdapter
        loadSmartData()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        super.loadSmartData(refreshLayout, isRefresh)
        mViewModel.loadCompoundPagerDetails("12222")
    }

    override fun initEvent() {
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mCompoundPagerLiveData.observe(this) {
            val data = it.synthesisMerchandiseBO
            UICompat.notifyAdapterDateChanged(null, mAdapter, true, mData, data)
        }
    }

    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        MetaViewCompat.setStatusBarMargin(mViewBinding.pvTitle, this)
    }
}