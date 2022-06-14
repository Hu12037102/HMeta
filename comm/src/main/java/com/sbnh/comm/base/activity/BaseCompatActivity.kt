package com.sbnh.comm.base.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sbnh.comm.R
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.databinding.BaseParentLoadingViewBinding
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.view.EmptyLayout

abstract class BaseCompatActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity() {
    protected val mViewBinding: VB by lazy { getViewBinding() }
    protected open val mViewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }
    protected var mLoadingViewBinding: BaseParentLoadingViewBinding? = null
    protected var mEmptyView: EmptyLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  mViewBinding = getViewBinding()
        val rootView = mViewBinding?.root
        initEmptyLoadingView(rootView)
        setContentView(rootView)
        init()
        LogUtils.w(LogUtils.TAG, "onCreate:${this.javaClass.simpleName}")
    }

    private fun init() {
        // mViewModel = ViewModelProvider(this)[getViewModelClass()]
        initView()
        initData()
        initEvent()
        initObserve()
    }

    protected abstract fun initView()

    protected abstract fun getViewBinding(): VB
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun initData()
    protected abstract fun initEvent()
    protected open fun initObserve() {
        mViewModel.mToastLiveData.observe(this) {
            showToast(it)
        }
        mViewModel.mUserInfoLiveData.observe(this) {
            resultUserInfo(it)
        }
    }

    protected open fun isLoadEmptyView(): Boolean = false

    private fun initEmptyLoadingView(rootView: View?) {
        if (rootView is ViewGroup) {
            if (isLoadEmptyView()) {
                mEmptyView = EmptyLayout(this)
                rootView.addView(
                    mEmptyView,
                    0,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }
            mLoadingViewBinding =
                BaseParentLoadingViewBinding.inflate(layoutInflater, rootView, false)
            rootView.addView(mLoadingViewBinding?.root)
        }
    }

    protected open fun resultUserInfo(userInfoEntity: UserInfoEntity) {

    }

}