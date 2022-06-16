package com.sbnh.my.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.manger.ActivityCompatManger
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ActivityMyAccountInfoBinding
import com.sbnh.my.viewbinding.MyAccountInfoViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 15:43
 * 更新时间: 2022/6/16 15:43
 * 描述:我的账号详情Activity
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_MY_ACCOUNT_INFO)
class MyAccountInfoActivity :
    BaseCompatActivity<ActivityMyAccountInfoBinding, MyAccountInfoViewModel>() {
    override fun getViewBinding(): ActivityMyAccountInfoBinding =
        ActivityMyAccountInfoBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyAccountInfoViewModel> =
        MyAccountInfoViewModel::class.java

    override fun initView() {
        MetaViewCompat.setClickButton(mViewBinding.atvExitLogin, Contract.DP.VALUE_8F)
    }

    override fun initData() {
        mViewModel.loadUserInfo()
    }

    override fun initEvent() {
        mViewBinding.atvExitLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                mViewModel.exitLogin()
            }

        })
    }

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        userInfoEntity?.let {
            GlideCompat.loadImage(
                it.header,
                mViewBinding.civHead,
                com.sbnh.comm.R.mipmap.icon_my_not_login
            )
            UICompat.setText(mViewBinding.pvNickname.getRightView(), it.nickName)
            UICompat.setText(mViewBinding.pvPhone.getRightView(), it.mobile)
        }
    }
}