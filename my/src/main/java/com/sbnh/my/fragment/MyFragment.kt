package com.sbnh.my.fragment

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.my.R
import com.sbnh.my.adapter.MyTabAdapter
import com.sbnh.my.databinding.FragmentMyBinding
import com.sbnh.my.viewbinding.MyViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 15:42
 * 更新时间: 2022/6/13 15:42
 * 描述:我的fragment
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY)
class MyFragment : BaseCompatFragment<FragmentMyBinding, MyViewModel>() {
    private val mTabData by lazy { mViewModel.createTabs() }
    private var mTabAdapter: MyTabAdapter? = null
    private val mLineCount = 4
    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun initView() {
        mViewBinding.rvTab.layoutManager = GridLayoutManager(context, mLineCount)
    }

    override fun initData() {
        mViewModel.getUserInfo()
        iniTabAdapter()
    }

    private fun iniTabAdapter() {
        mTabAdapter = MyTabAdapter(DataCompat.checkContext(context), mTabData, mLineCount)
        mViewBinding.rvTab.adapter = mTabAdapter
    }

    override fun initEvent() {
    }

    override fun initObserve() {
        super.initObserve()
    }

    override fun onStart() {
        super.onStart()
        mViewModel.getUserInfo()
    }

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        if (UserInfoStore.isLogin(userInfoEntity)) {
            mViewBinding.clLogin.visibility = View.VISIBLE
            mViewBinding.clNotLogin.visibility = View.GONE

        } else {
            mViewBinding.clNotLogin.visibility = View.VISIBLE
            mViewBinding.clLogin.visibility = View.GONE
            GlideCompat.loadImage(com.sbnh.comm.R.mipmap.icon_my_not_login, mViewBinding.civHead)
            UICompat.setText(
                mViewBinding.atvNotLoginTitle,
                DataCompat.getResString(com.sbnh.comm.R.string.not_login)
            )
            UICompat.setText(
                mViewBinding.atvNotLoginDesc,
                DataCompat.getResString(com.sbnh.comm.R.string.login_read_you_digital_collection)
            )
        }


    }
}