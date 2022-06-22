package com.sbnh.my.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.databinding.ActivityOfficialAccountsBinding
import com.sbnh.my.viewmodel.OfficialAccountsViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 14:28
 * 更新时间: 2022/6/22 14:28
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_OFFICIAL_ACCOUNTS)
class OfficialAccountsActivity :
    BaseCompatActivity<ActivityOfficialAccountsBinding, OfficialAccountsViewModel>() {
    override fun getInActivityAnimationRes(): Int {
        return com.sbnh.comm.R.anim.anim_small_to_large
    }

    override fun getOutActivityAnimationRes(): Int {
        return com.sbnh.comm.R.anim.anim_large_to_small
    }

    companion object {
        const val Q_CODE_URL = "https://test.cdn.sbnh.cn/nft_page/main/code.png"
    }

    override fun getViewBinding(): ActivityOfficialAccountsBinding =
        ActivityOfficialAccountsBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<OfficialAccountsViewModel> =
        OfficialAccountsViewModel::class.java

    override fun initView() {
        GlideCompat.createGlide(Q_CODE_URL, this).skipMemoryCache(true)
            .diskCacheStrategy(
                DiskCacheStrategy.NONE
            ).into(mViewBinding.aivQCode)


    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.aivQCode.setOnLongClickListener {
            mViewModel.savePicture(Q_CODE_URL)
            false
        }
        mViewBinding.root.setOnClickListener {
            MetaViewCompat.finishActivity(this)
        }

    }
}