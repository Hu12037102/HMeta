package com.sbnh.comm.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewbinding.ViewBinding
import com.google.permission.fragment.PermissionFragment
import com.sbnh.comm.R
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.view.EmptyLayout

abstract class BaseCompatFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment() {
    protected val mViewBinding: VB by lazy { getViewBinding() }
    protected val mViewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }
    protected var mRootView: View? = null

    companion object {
        const val TAG = LogUtils.TAG
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtils.w(TAG, "onAttach:${this.javaClass.simpleName}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.w(TAG, "onCreate:${this.javaClass.simpleName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.w(TAG, "onCreateView:${this.javaClass.simpleName}")
        this.mRootView = mViewBinding.root
        initEmptyLoadingView(context, mRootView)
        init()
        return mRootView
    }

    private fun init() {

        initView()
        initData()
        initEvent()
        initObserve()
    }

    protected abstract fun getViewBinding(): VB
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initEvent()
    protected open fun initObserve() {
        mViewModel.mToastLiveData.observe(viewLifecycleOwner) {
            showToast(it)
            LogUtils.w(TAG, "我收到数据回调:${this.javaClass.simpleName}----$it")
        }
        mViewModel.mUserInfoLiveData.observe(viewLifecycleOwner) {
            LogUtils.w(TAG, "我收到数据回调:${this.javaClass.simpleName}----$it")
            resultUserInfo(it)
        }
    }

    protected open fun isLoadEmptyView(): Boolean = false
    private fun initEmptyLoadingView(context: Context?, rootView: View?) {
        if (context == null || rootView == null) {
            return
        }
        if (rootView is ViewGroup) {
            if (isLoadEmptyView()) {
                val emptyLayout = EmptyLayout(context)
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
                LayoutInflater.from(context)
                    .inflate(R.layout.base_parent_loading_view, rootView, false)
            rootView.addView(loadingParentView)
        }
    }

    override fun onStart() {
        super.onStart()
        LogUtils.w(TAG, "onStart:${this.javaClass.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.w(TAG, "onResume:${this.javaClass.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.w(TAG, "onPause:${this.javaClass.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.w(TAG, "onStop:${this.javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.w(TAG, "onDestroy:${this.javaClass.simpleName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.w(TAG, "onDestroyView:${this.javaClass.simpleName}")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtils.w(TAG, "onDetach:${this.javaClass.simpleName}")
    }

    protected open fun resultUserInfo(userInfoEntity: UserInfoEntity?) {

    }
}