package com.sbnh.my.activity

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ActivitySettingBinding
import com.sbnh.my.viewmodel.SettingViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/16 9:03
 * 更新时间: 2022/6/16 9:03
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_SETTING)
class SettingActivity : BaseCompatActivity<ActivitySettingBinding, SettingViewModel>() {
    override fun getViewBinding(): ActivitySettingBinding =
        ActivitySettingBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<SettingViewModel> = SettingViewModel::class.java

    override fun initView() {
        ViewCompat.setBackground(mViewBinding.pvAccount, createItemBackground())
        ViewCompat.setBackground(mViewBinding.pvService, createItemBackground())
        ViewCompat.setBackground(mViewBinding.pvRealName, createItemBackground())
        ViewCompat.setBackground(
            mViewBinding.pvSettingPassword,
            createItemBackground()
        )
        ViewCompat.setBackground(mViewBinding.pvBankCard, createItemBackground())
    }

    override fun initData() {
        mViewModel.loadUserInfo()
    }

    override fun initEvent() {
        mViewBinding.pvAccount.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_MY_ACCOUNT_INFO)
            }

        })
        mViewBinding.pvSettingPassword.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_SET_PAYMENT_PASSWORD)
            }

        })
        mViewBinding.pvBankCard.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_BANK_CARD_LIST)
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
    }

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        val textView = mViewBinding.pvRealName.getRightView()
        UICompat.setTextColor(
            textView,
            MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF717171)
        )
        UICompat.setTextSize(textView, 15f)
        if (UserInfoStore.isRealName(userInfoEntity)) {
            UICompat.setText(
                textView,
                DataCompat.getResString(com.sbnh.comm.R.string.authentication)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                com.sbnh.comm.R.mipmap.icon_real_name,
                0,
                0,
                0
            )
            textView.compoundDrawablePadding = PhoneCompat.dp2px(this, 5f)
        } else {
            UICompat.setText(
                mViewBinding.pvRealName.getRightView(),
                DataCompat.getResString(com.sbnh.comm.R.string.not_authentication)
            )

        }

    }

    private fun createItemBackground(): Drawable {

        val drawable = GradientDrawableCompat.create()
        drawable.setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFF363639))
        drawable.cornerRadius = PhoneCompat.dp2px(this, 8f).toFloat()
        return drawable
    }

}