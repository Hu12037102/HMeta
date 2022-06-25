package com.sbnh.comm.other.arouter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.manger.ActivityCompatManger

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
    fun startCollectionDetailsActivity(id: String?) {
        ARouters.build(ARouterConfig.Path.Home.ACTIVITY_COLLECTION_DETAILS)
            .withString(ARouterConfig.Key.ID, id)
            .navigation()
    }

    @JvmStatic
    fun startOrderDetailsActivity(id: String?) {
        ARouters.build(ARouterConfig.Path.Order.ACTIVITY_ORDER_DETAILS)
            .withString(ARouterConfig.Key.ID, id)
            .navigation()
    }

    @JvmStatic
    fun startCollectionNumDetailsActivity(entity: MyCollectionEntity){
        ARouters.build(ARouterConfig.Path.My.ACTIVITY_COLLECTION_NUM_DETAILS)
            .withParcelable(ARouterConfig.Key.MY_COLLECTION, entity)
            .navigation()
    }

    @JvmStatic
    fun startGiveCollectionActivity(id: String?){
        ARouters.build(ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION)
            .withString(ARouterConfig.Key.ID, id)
            .navigation()
    }

    @JvmStatic
    fun startBrowserActivity(context: Context?, url: String?) {
        try {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (context is Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context?.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}