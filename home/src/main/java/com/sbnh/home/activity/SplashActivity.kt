package com.sbnh.home.activity

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.viewmodel.BaseOrderViewModel
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.home.databinding.ActivitySpashBinding
import com.sbnh.home.viewmodel.SplashViewModel
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/27 13:48
 * 更新时间: 2022/6/27 13:48
 * 描述:引导页activity
 */
@Route(path = ARouterConfig.Path.Home.ACTIVITY_SPLASH)
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseCompatActivity<ActivitySpashBinding, SplashViewModel>() {
    override fun getViewBinding(): ActivitySpashBinding =
        ActivitySpashBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        GlideCompat.loadImage(
            com.sbnh.comm.R.mipmap.icon_comm_splash_background,
            mViewBinding.aivContent
        )
        mViewBinding.aivContent.postDelayed(mRunnable, 800)

    }

    private val mRunnable = Runnable {
        ARouters.startActivity(ARouterConfig.Path.Main.ACTIVITY_MAIN)
        MetaViewCompat.finishActivity(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.aivContent.removeCallbacks(mRunnable)
    }
}