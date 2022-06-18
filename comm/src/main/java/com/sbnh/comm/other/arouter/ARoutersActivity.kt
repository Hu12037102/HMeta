package com.sbnh.comm.other.arouter

import com.sbnh.comm.manger.ActivityCompatManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 16:36
 * 更新时间: 2022/6/16 16:36
 * 描述:
 */
object ARoutersActivity {
    @JvmStatic
    fun startLoginActivity() {
        ARouters.startActivity(ARouterConfig.Path.Login.ACTIVITY_LOGIN)
    }

    @JvmStatic
    fun startMainActivity() {
        ActivityCompatManger.get().clear()
        ARouters.startActivity(ARouterConfig.Path.Main.ACTIVITY_MAIN)
    }

}