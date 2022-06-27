package com.sbnh.comm.base.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sbnh.comm.base.viewmodel.BaseViewModel
import com.sbnh.comm.databinding.BaseParentLoadingViewBinding
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.other.smart.SmartRefreshLayoutCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.view.EmptyLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

abstract class BaseCompatActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity() {
    private var mRefreshLayout: RefreshLayout? = null
    protected val mViewBinding: VB by lazy {
        getViewBinding()
    }
    protected open val mViewModel: VM by lazy { ViewModelProvider(this)[getViewModelClass()] }
    protected var mLoadingViewBinding: BaseParentLoadingViewBinding? = null
    protected var mEmptyView: EmptyLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  mViewBinding = getViewBinding()
        val rootView = mViewBinding.root
        initParentView(rootView)
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

    protected abstract fun getViewBinding(): VB
    protected abstract fun getViewModelClass(): Class<VM>
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initEvent()
    protected open fun initObserve() {
        mViewModel.mToastLiveData.observe(this) {
            showToast(it)
        }
        mViewModel.mUserInfoLiveData.observe(this) {
            resultUserInfo(it)
        }
        mViewModel.mGainMessageCodeLiveData.observe(this) {
            resultGainMessageCode()
        }
        mViewModel.mLoadingLiveData.observe(this) {
            if (it) {
                mLoadingViewBinding?.cpbLoading?.show()
            } else {
                /*  mLoadingViewBinding?.cpbLoading?.postDelayed(
                      { mLoadingViewBinding?.cpbLoading?.hide() },
                      500
                  )*/
                mLoadingViewBinding?.cpbLoading?.hide()
            }

        }
        mViewModel.mPublicLiveData.observe(this) {
            resultPublicData(it)
        }
    }

    protected open fun isLoadEmptyView(): Boolean = false

    private fun initParentView(rootView: View?) {
        if (rootView is ViewGroup) {
            if (rootView is SmartRefreshLayout) {
                initRefreshLayout(rootView)

            } else {
                for (i in 0 until rootView.childCount) {
                    val childView = rootView.getChildAt(i)
                    if (childView is SmartRefreshLayout) {
                        initRefreshLayout(childView)
                        break
                    }
                }
            }

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
            mLoadingViewBinding?.cpbLoading?.hide()
            rootView.addView(mLoadingViewBinding?.root)
        }
    }

    private fun initRefreshLayout(refreshLayout: SmartRefreshLayout) {
        this.mRefreshLayout = refreshLayout
        SmartRefreshLayoutCompat.initDefault(refreshLayout)
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mViewModel.mPagerNum = 0
                mViewModel.isRefresh = true
                loadSmartData(refreshLayout, mViewModel.isRefresh)
                //  refreshLayout.finishRefresh()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mViewModel.isRefresh = false
                loadSmartData(refreshLayout, mViewModel.isRefresh)
                // refreshLayout.finishLoadMore()
            }

        })
    }

    protected open fun resultUserInfo(userInfoEntity: UserInfoEntity?) {

    }

    protected open fun resultGainMessageCode() {}


    protected open fun resultPublicData(@BaseViewModel.ViewModelStatus it: Int) {
        if (it == BaseViewModel.STATUE_REQUEST_END) {
            mRefreshLayout?.finishRefresh()
            mRefreshLayout?.finishLoadMore()
        }
    }
}