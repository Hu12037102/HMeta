package com.sbnh.comm.other.arouter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import com.sbnh.comm.Contract
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
    fun createLoginIntent(context: Context) =
        ARouters.intent(context, ARouterConfig.Path.Login.ACTIVITY_LOGIN)

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
    fun startCollectionDetailsActivity(
        id: String?,
        otherId: String? = null,
        collectionDetailsStatus: Int = Contract.PutOrderType.OFFICIAL
    ) {
        ARouters.build(ARouterConfig.Path.Home.ACTIVITY_COLLECTION_DETAILS)
            .withString(ARouterConfig.Key.ID, id)
            .withString(ARouterConfig.Key.OTHER_ID, otherId)
            .withInt(ARouterConfig.Key.STATUS, collectionDetailsStatus)
            .navigation()
    }

    /*   @JvmStatic
       fun startCollectionDetailsActivity(id: String?, cid: String?) {
           ARouters.build(ARouterConfig.Path.Home.ACTIVITY_COLLECTION_DETAILS)
               .withString(ARouterConfig.Key.ID, id)
               .withString(ARouterConfig.Key.CID, cid)
               .navigation()
       }*/

    @JvmStatic
    fun startOrderDetailsActivity(id: String?, putOrderType: Int = Contract.PutOrderType.OFFICIAL) {
        ARouters.build(ARouterConfig.Path.Order.ACTIVITY_ORDER_DETAILS)
            .withString(ARouterConfig.Key.ID, id)
            .withInt(ARouterConfig.Key.TYPE, putOrderType)
            .navigation()
    }

    @JvmStatic
    fun startCollectionNumDetailsActivity(entity: MyCollectionEntity) {
        ARouters.build(ARouterConfig.Path.My.ACTIVITY_COLLECTION_NUM_DETAILS)
            .withParcelable(ARouterConfig.Key.MY_COLLECTION, entity)
            .navigation()
    }

    @JvmStatic
    fun startGiveCollectionActivity(cid: String?, merchandiseId: String?) {
        ARouters.build(ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION)
            .withString(ARouterConfig.Key.OTHER_ID, cid)
            .withString(ARouterConfig.Key.ID, merchandiseId)
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

    @JvmStatic
    fun startPictureSaveActivity(picturePath: String) {
        ARouters.build(ARouterConfig.Path.My.ACTIVITY_PICTURE_SAVE)
            .withString(ARouterConfig.Key.PICTURE_PATH, picturePath)
            .navigation()
    }


    @JvmStatic
    fun installPackage(context: Context, uri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            if (context is Application) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}