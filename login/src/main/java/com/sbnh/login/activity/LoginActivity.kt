package com.sbnh.login.activity

import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.*
import com.sbnh.comm.entity.request.RequestLoginEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.SpanTextHelper

import com.sbnh.login.databinding.ActivityLoginBinding
import com.sbnh.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 19:51
 * 更新时间: 2022/6/14 19:51
 * 描述:
 */
@Route(path = ARouterConfig.Path.Login.ACTIVITY_LOGIN)
class LoginActivity : BaseCompatActivity<ActivityLoginBinding, LoginViewModel>() {
    private var isCheckAgreement = false
    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getInActivityAnimationRes(): Int {
        return com.sbnh.comm.R.anim.anim_bottom_to_center
    }

    override fun getOutActivityAnimationRes(): Int {
        return com.sbnh.comm.R.anim.anim_center_to_bottom
    }

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
        SpanTextHelper.with().append(DataCompat.getResString(com.sbnh.comm.R.string.not_account_go))
            .appendBlank()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.register))
            .setColor(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCC59DA))
            .crete(mViewBinding.atvGoRegister)
        MetaViewCompat.setClickButton(
            mViewBinding.atvLogin,
            Contract.DP.VALUE_50F
        )
        SpanTextHelper.with()
            .append(DataCompat.getResString(com.sbnh.comm.R.string.my_have_read_and_sure))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.user_agreement))
            .setClick(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCC59DA)) {
                ARoutersActivity.startWebContentActivity("http://www.baidu.com")
            }
            .append(DataCompat.getResString(com.sbnh.comm.R.string.and))
            .append(DataCompat.getResString(com.sbnh.comm.R.string.privacy_policy))
            .setClick(MetaViewCompat.getColor(com.sbnh.comm.R.color.colorFFCC59DA)) {

            }
            .crete(mViewBinding.atvAgreement)
    }


    override fun initData() {

    }


    override fun initEvent() {
        mViewBinding.atvGainCode.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val phoneNumber =
                    MetaViewCompat.getTextViewText(mViewBinding.aetPhone)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val request = RequestMessageCodeEntity(DataCompat.toString(phoneNumber))
                mViewModel.gainMessageCode(request)
                //   mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
            }
        })
        mViewBinding.atvGoRegister.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.Login.ACTIVITY_REGISTER)
            }

        })
        mViewBinding.aivCheckAgreement.setOnClickListener {
            isCheckAgreement = !isCheckAgreement
            if (isCheckAgreement) {
                UICompat.setImageRes(
                    mViewBinding.aivCheckAgreement,
                    com.sbnh.comm.R.mipmap.icon_comm_check
                )
            } else {
                UICompat.setImageRes(
                    mViewBinding.aivCheckAgreement,
                    com.sbnh.comm.R.mipmap.icon_comm_normal
                )
            }
        }
        mViewBinding.atvLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {

                val phoneNumber =
                    MetaViewCompat.getTextViewText(mViewBinding.aetPhone)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val messageCode = MetaViewCompat.getTextViewText(mViewBinding.aetGainCode)
                val isEmptyCode =
                    MetaViewCompat.textViewTextIsEmpty(mViewBinding.aetGainCode)
                if (isEmptyCode) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
                if (!isCheckAgreement) {
                    showToast(com.sbnh.comm.R.string.please_agree_user_agreement)
                    return
                }
                val request = RequestLoginEntity(
                    DataCompat.toString(phoneNumber),
                    DataCompat.toString(messageCode)
                )
                mViewModel.login(request)
            }

        })
        mViewBinding.root.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                MetaViewCompat.hideSoftKeyBoard(mViewBinding.root)
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
        mViewModel.mLoginLiveData.observe(this) {
            lifecycleScope.launch {
                UserInfoStore.get().putEntity(it)
                ARoutersActivity.loginActivityComplete()
            }

        }

    }

    override fun resultGainMessageCode() {
        mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
    }


    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        MetaViewCompat.showSoftKeyBoard(mViewBinding.aetPhone)
    }


}