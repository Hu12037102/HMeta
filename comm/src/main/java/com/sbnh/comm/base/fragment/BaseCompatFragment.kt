package com.sbnh.comm.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.databinding.BaseParentLoadingViewBinding
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.smart.SmartRefreshLayoutCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.view.EmptyLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.launch

abstract class BaseCompatFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment() {
    protected val mViewBinding: VB by lazy { getViewBinding() }
    protected val mViewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }
    protected var mEmptyLayout: EmptyLayout? = null
    protected var mLoadingViewBinding: BaseParentLoadingViewBinding? = null
    protected var mRootView: View? = null
    private var isFirstCreate = true

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
        // initEmptyLoadingView(context, mRootView)
        //init()
        return mRootView
    }

    private fun init() {
        initParentView(context, mRootView)
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
    private fun initParentView(context: Context?, rootView: View?) {
        if (context == null || rootView == null) {
            return
        }
        if (rootView is ViewGroup) {
            for (i in 0 until rootView.childCount) {
                val childView = rootView.getChildAt(i)
                if (childView is SmartRefreshLayout) {
                    SmartRefreshLayoutCompat.initDefault(childView)
                    break
                }
            }
            if (isLoadEmptyView()) {
                LogUtils.w(
                    "initEmptyLoadingView--",
                    "${isLoadEmptyView()}--${this.javaClass.simpleName}"
                )
                mEmptyLayout = EmptyLayout(context)
                rootView.addView(
                    mEmptyLayout, 0,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }
            mLoadingViewBinding =
                BaseParentLoadingViewBinding.inflate(layoutInflater, rootView, false)
            /*val loadingParentView =
                LayoutInflater.from(context)
                    .inflate(R.layout.base_parent_loading_view, rootView, false)*/
            mLoadingViewBinding?.cpbLoading?.hide()
            rootView.addView(mLoadingViewBinding?.root)
        }
    }

    override fun onStart() {
        super.onStart()
        LogUtils.w(TAG, "onStart:${this.javaClass.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        if (isFirstCreate) {
            init()
            isFirstCreate = false
        }
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