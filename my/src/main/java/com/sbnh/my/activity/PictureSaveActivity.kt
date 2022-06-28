package com.sbnh.my.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.my.databinding.ActivityPictureSaveBinding
import com.sbnh.my.viewmodel.OfficialAccountsViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/22 14:28
 * 更新时间: 2022/6/22 14:28
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_PICTURE_SAVE)
class PictureSaveActivity :
    BaseCompatActivity<ActivityPictureSaveBinding, OfficialAccountsViewModel>() {
    private var mPath = ""
    override fun getInActivityAnimationRes(): Int {
        return com.sbnh.comm.R.anim.anim_small_to_large
    }

    override fun getOutActivityAnimationRes(): Int {
        return com.sbnh.comm.R.anim.anim_large_to_small
    }


    override fun getViewBinding(): ActivityPictureSaveBinding =
        ActivityPictureSaveBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<OfficialAccountsViewModel> =
        OfficialAccountsViewModel::class.java

    override fun initView() {


    }

    override fun initData() {
        mPath = intent?.getStringExtra(ARouterConfig.Key.PICTURE_PATH) ?: ""
        LogUtils.w("initData--", mPath)
        GlideCompat.createGlide(mPath, this).skipMemoryCache(true)
            .diskCacheStrategy(
                DiskCacheStrategy.NONE
            )
            .placeholder(com.sbnh.comm.R.color.colorWindowBackground)
            .into(mViewBinding.aivQCode)
    }

    override fun initEvent() {
        mViewBinding.aivQCode.setOnLongClickListener {
            mViewModel.savePicture(mPath)
            false
        }
        mViewBinding.root.setOnClickListener {
            MetaViewCompat.finishActivity(this)
        }

    }
}