package com.sbnh.comm.base.callback

import android.view.View

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/12 20:52
 * 更新时间: 2022/6/12 20:52
 * 描述:
 */
interface OnRecyclerItemClickListener {
    fun onClickItem(view: View, position: Int)
}