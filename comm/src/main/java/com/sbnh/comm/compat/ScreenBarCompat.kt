package com.sbnh.comm.compat

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 15:23
 * 更新时间: 2022/6/15 15:23
 * 描述:
 */
object ScreenBarCompat {

    @JvmStatic
    fun changeStatusBar(isShow: Boolean, rootView:View) {
       /*   val decorView: View = window.decorView
          val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                  or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
          decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT*/

        ViewCompat.getWindowInsetsController(rootView)?.let {
            if (isShow) {
                it.show(WindowInsetsCompat.Type.statusBars())
            } else {
                it.hide(WindowInsetsCompat.Type.statusBars())
            }

        }
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (isShow) {
                window.insetsController?.show(WindowInsetsCompat.Type.statusBars())
            } else {
                window.insetsController?.hide(WindowInsetsCompat.Type.statusBars())
            }

        }else{
            ViewCompat.getWindowInsetsController(window.decorView)?.let {
                if (isShow) {
                    it.show(WindowInsetsCompat.Type.statusBars())
                } else {
                    it.hide(WindowInsetsCompat.Type.statusBars())
                }

            }
        }*/

    }
}