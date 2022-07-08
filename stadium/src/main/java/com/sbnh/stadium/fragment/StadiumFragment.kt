package com.sbnh.stadium.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.stadium.R
import com.sbnh.stadium.databinding.FragmentStadiumBinding
import com.sbnh.stadium.viewmodel.StadiumViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/8 17:26
 * 更新时间: 2022/7/8 17:26
 * 描述:
 */
@Route(path = ARouterConfig.Path.Stadium.FRAGMENT_STADIUM)
class StadiumFragment : BaseCompatFragment<FragmentStadiumBinding, StadiumViewModel>() {
    override fun getViewBinding(): FragmentStadiumBinding =
        FragmentStadiumBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<StadiumViewModel> = StadiumViewModel::class.java

    override fun initView() {
        GlideCompat.loadWarpImage(R.mipmap.icon_stadium_test_content,mViewBinding.aivContent)
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.aivContent.setOnClickListener { showToast(com.sbnh.comm.R.string.the_function_is_not_available) }
    }
}