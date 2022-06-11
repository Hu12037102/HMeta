package com.sbnh.comm.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sbnh.comm.R
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.weight.view.EmptyLayout

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected lateinit var mViewBinding: VB
    protected lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = getViewBinding()
        val rootView = mViewBinding.root
        initEmptyLoadingView(rootView)
        setContentView(rootView)
        init()

    }

    private fun init() {
        mViewModel = ViewModelProvider(this).get(getViewModelClass())
        initData()
        initEvent()
        initObserve()
    }

    protected abstract fun getViewBinding(): VB
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun initData()
    protected abstract fun initEvent()
    protected abstract fun initObserve()
    protected open fun isLoadEmptyView(): Boolean = false

    private fun initEmptyLoadingView(rootView: View) {
        if (rootView is ViewGroup) {
            if (isLoadEmptyView()) {
                val emptyLayout = EmptyLayout(this)
                rootView.addView(
                    emptyLayout,
                    0,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }

            val loadingParentView =
                LayoutInflater.from(this)
                    .inflate(R.layout.base_parent_loading_view, rootView, false)
            rootView.addView(loadingParentView)
        }
    }


}