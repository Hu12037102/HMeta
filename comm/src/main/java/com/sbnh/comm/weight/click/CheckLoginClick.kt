package com.sbnh.comm.weight.click

import android.view.View
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARoutersActivity
import kotlinx.coroutines.*

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 9:51
 * 更新时间: 2022/6/15 9:51
 * 描述:
 */
abstract class CheckLoginClick : DelayedClick {
    private var checkLogin = true

    constructor(checkLogin: Boolean) {
        this.checkLogin = checkLogin
    }

    constructor()

    private val scope = MainScope()
    override fun onDelayedClick(v: View?) {
        if (checkLogin) {
            scope.launch(Dispatchers.IO) {
                val isLogin = UserInfoStore.get().isLogin()
                withContext(Dispatchers.Main) {
                    if (!isLogin) {
                        ARoutersActivity.startLoginActivity()
                    } else {
                        onCheckLoginClick(v)
                    }

                }

            }
        } else {
            onCheckLoginClick(v)
        }

    }

    abstract fun onCheckLoginClick(v: View?)
}