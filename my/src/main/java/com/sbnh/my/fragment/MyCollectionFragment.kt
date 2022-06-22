package com.sbnh.my.fragment

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.FragmentCollectionBinding
import com.sbnh.my.viewmodel.MyCollectionViewModel

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
        MetaViewCompat.setClickButton(mViewBinding.atvLogin, Contract.DP.VALUE_8F)
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadUserInfo()
    }

    override fun initEvent() {
        mViewBinding.atvLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARoutersActivity.startLoginActivity()
            }

        })
        mEmptyLayout?.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION)

            }

        })
    }

    override fun isLoadEmptyView(): Boolean = true
    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        if (!UserInfoStore.isLogin(userInfoEntity)) {
            mViewBinding.atvLogin.visibility = View.VISIBLE
        } else {
            mViewBinding.atvLogin.visibility = View.GONE
        }
    }
}