package com.sbnh.login.activity

import android.view.View
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.GradientDrawableCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.login.databinding.ActivityRegisterBinding
import com.sbnh.login.viewmodel.RegisterViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/15 14:49
 * 更新时间: 2022/6/15 14:49
 * 描述:
 */
@Route(path = ARouterConfig.Path.Login.ACTIVITY_REGISTER)
class RegisterActivity : BaseCompatActivity<ActivityRegisterBinding, RegisterViewModel>() {

    private var isCheckAgreement = false
    override fun getViewBinding(): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java
    override fun initView() {
        GlideCompat.loadImage(com.sbnh.comm.R.mipmap.icon_login_background, mViewBinding.aivContent)
        GlideCompat.loadWarpImage(com.sbnh.comm.R.mipmap.icon_login_logo, mViewBinding.aivLogo)
        ViewCompat.setBackground(
            mViewBinding.aetPhone,
            GradientDrawableCompat.createLoginInputDrawable()
        )
        ViewCompat.setBackground(
            mViewBinding.clMessageCode,
            GradientDrawableCompat.createLoginInputDrawable()
        )
        ViewCompat.setBackground(
            mViewBinding.aetInviteCode,
            GradientDrawableCompat.createLoginInputDrawable()
        )
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.have_account_go))
            .appendBlank()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.login))
            .setColor(com.sbnh.comm.compat.MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCC59DA))
            .crete(mViewBinding.atvGoLogin)
        com.sbnh.comm.compat.MetaViewCompat.setClickButton(
            mViewBinding.atvRegister,
            Contract.DP.VALUE_50F
        )
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.my_have_read_and_sure))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.user_agreement))
            .setClick(com.sbnh.comm.compat.MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCC59DA)) {

            }
            .append(DataCompat.getResString(com.sbnh.comm.R.string.and))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.privacy_policy))
            .setClick(com.sbnh.comm.compat.MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCC59DA)) {

            }
            .crete(mViewBinding.atvAgreement)
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.atvGainCode.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val phoneNumber =
                    com.sbnh.comm.compat.MetaViewCompat.getTextViewText(mViewBinding.aetPhone)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
            }
        })
        mViewBinding.atvGoLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.Login.ACTIVITY_LOGIN)
            }

        })
        mViewBinding.aivCheckAgreement.setOnClickListener {
            isCheckAgreement = !isCheckAgreement
            if (isCheckAgreement) {
                UICompat.setImageRes(
                    mViewBinding.aivCheckAgreement,
                    com.sbnh.comm.R.mipmap.icon_login_check_user_agreement
                )
            } else {
                UICompat.setImageRes(
                    mViewBinding.aivCheckAgreement,
                    com.sbnh.comm.R.mipmap.icon_login_normal_user_agreement
                )
            }
        }
        mViewBinding.atvRegister.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                if (!isCheckAgreement) {
                    showToast(com.sbnh.comm.R.string.please_again_user_agreement)
                    return
                }
                val phoneNumber =
                    com.sbnh.comm.compat.MetaViewCompat.getTextViewText(mViewBinding.aetPhone)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val isEmptyCode =
                    com.sbnh.comm.compat.MetaViewCompat.textViewTextIsEmpty(mViewBinding.aetGainCode)
                if (isEmptyCode) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mTimerLiveData.observe(this) {
            if (it.status == STATUS_RUNNING) {
                mViewBinding.atvGainCode.isEnabled = false
                UICompat.setText(
                    mViewBinding.atvGainCode, "${it.lastTimeLength}s"
                )
            } else {
                mViewBinding.atvGainCode.isEnabled = true
                UICompat.setText(
                    mViewBinding.atvGainCode, DataCompat.getResString(
                        com.sbnh.comm.R.string.gain_message_code
                    )
                )
            }

        }
    }
}