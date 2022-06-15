package com.sbnh.my.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.compat.ViewCompat
import com.sbnh.comm.manger.ActivityCompatManger
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.FragmentCollectionBinding
import com.sbnh.my.viewbinding.MyCollectionViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 16:57
 * 更新时间: 2022/6/14 16:57
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION)
class MyCollectionFragment :
    BaseCompatFragment<FragmentCollectionBinding, MyCollectionViewModel>() {
    override fun getViewBinding(): FragmentCollectionBinding =
        FragmentCollectionBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyCollectionViewModel> =
        MyCollectionViewModel::class.java

    override fun initView() {
        ViewCompat.setClickButton(mViewBinding.atvLogin,Contract.DP.VALUE_8F)
    }

    override fun initData() {
        val isLogin: Boolean = arguments?.getBoolean(ARouterConfig.Key.HAS_LOGIN, false) ?: false
        if (isLogin) {
            mViewBinding.atvLogin.visibility = View.GONE
        } else {
            mViewBinding.atvLogin.visibility = View.VISIBLE
        }

    }

    override fun initEvent() {
        mViewBinding.atvLogin.setOnClickListener(object :DelayedClick(){
            override fun onDelayedClick(v: View?) {
                ActivityCompatManger.startToLoginActivity()
            }

        })
    }
}