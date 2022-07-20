package com.sbnh.comm.tool

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.view.EmptyLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/19 14:18
 * 更新时间: 2022/7/19 14:18
 * 描述:
 */
class ViewTools {
    private val mData = ArrayList<View>()

   private fun traversalView(parentView: View?) {
        if (parentView == null) {
            return
        }
       mData.add(parentView)
        if (parentView is ViewGroup) {
            for (i in 0 until parentView.childCount) {
                val view = parentView.getChildAt(i)
                traversalView(view)
            }
        }

    }


    private fun showRecyclerViewDataEmptyView(emptyView: EmptyLayout?) {
        if (emptyView == null) {
            return
        }
        for (view in mData) {
            if (view is RecyclerView) {
                val adapter = view.adapter
                if (adapter is BaseRecyclerAdapter<*> && adapter.itemCount > 0) {
                    emptyView.hide()
                    return
                }
            }
        }
        emptyView.show()
    }


    companion object {
        @JvmStatic
        fun showEmptyView(parentView: View?, emptyView: EmptyLayout?) {
            val tools = ViewTools()
            tools.traversalView(parentView)
            tools.showRecyclerViewDataEmptyView(emptyView)
        }
    }
}