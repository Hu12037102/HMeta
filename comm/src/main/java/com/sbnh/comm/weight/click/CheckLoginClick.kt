package com.sbnh.comm.weight.click

import android.view.View
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.manger.ActivityCompatManger
import kotlinx.coroutines.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 9:51
 * 更新时间: 2022/6/15 9:51
 * 描述:
 */
abstract class CheckLoginClick : DelayedClick() {

    override fun onDelayedClick(v: View?) {
        MainScope().launch(Dispatchers.IO) {
            val isLogin = UserInfoStore.get().isLogin()
            withContext(Dispatchers.Main) {
                if (!isLogin) {
                    ActivityCompatManger.startToLoginActivity()
                } else {
                    onCheckLoginClick(v)

                }

            }

        }
    }

    abstract fun onCheckLoginClick(v: View?)
}