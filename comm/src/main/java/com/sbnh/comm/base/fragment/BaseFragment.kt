package com.sbnh.comm.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sbnh.comm.R
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.view.EmptyLayout

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    protected var mViewBinding: VB? = null
    protected var mViewModel: VM? = null
    protected var mRootView: View? = null

    companion object {
        const val TAG = "BaseFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtils.w(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.w(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.w(TAG, "onCreateView")
        mViewBinding = getViewBinding()
        this.mRootView = mViewBinding?.root
        initEmptyLoadingView(context, mRootView)
        init()
        return mRootView
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
        LogUtils.w(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.w(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.w(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.w(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.w(TAG, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.w(TAG, "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
    }
}