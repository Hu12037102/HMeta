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
    fun loginActivityComplete() {
        ActivityCompatManger.get().removeLoginAndRegisterActivity()
        //ARouters.startActivity(ARouterConfig.Path.Main.ACTIVITY_MAIN)
    }

    @JvmStatic
    fun startWebContentActivity(url: String?) {
        ARouters.build(ARouterConfig.Path.Comm.ACTIVITY_WEB_CONTENT)
            .withString(ARouterConfig.Key.WEB_URL, url).navigation()
    }

    @JvmStatic
    fun startCollectionDetailsActivity(id:String?){
        ARouters.build(ARouterConfig.Path.Home.ACTIVITY_COLLECTION_DETAILS)
            .withString(ARouterConfig.Key.ID, id)
            .navigation()
    }
    @JvmStatic
    fun startOrderDetailsActivity(id: String?){
        ARouters.build(ARouterConfig.Path.Order.ACTIVITY_ORDER_DETAILS)
            .withString(ARouterConfig.Key.ID, id)
            .navigation()
    }
}