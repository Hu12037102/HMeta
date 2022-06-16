package com.sbnh.comm.other.arouter

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
    fun startLoginActivity(){
        ARouters.startActivity(ARouterConfig.Path.Login.ACTIVITY_LOGIN)
    }

}