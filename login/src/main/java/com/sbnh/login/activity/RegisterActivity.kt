package com.sbnh.login.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.Contract
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.*
import com.sbnh.comm.entity.base.STATUS_RUNNING
import com.sbnh.comm.entity.other.CaptchaCheckResultEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.comm.entity.request.RequestRegisterEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.other.tencent.CaptchaDialogHelper
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.comm.weight.text.PhoneNumberWatcher
import com.sbnh.comm.weight.text.SpanTextHelper
import com.sbnh.login.databinding.ActivityRegisterBinding
import com.sbnh.login.viewmodel.RegisterViewModel
import com.tencent.captchasdk.TCaptchaDialog
import kotlinx.coroutines.launch

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
        mViewBinding.aetPhone.addTextChangedListener(mPhoneNumberTextWatcher)
        mViewBinding.atvGainCode.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val phoneNumber =
                    MetaViewCompat.getTextViewText(mViewBinding.aetPhone, true)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                CaptchaDialogHelper.showDialog(this@RegisterActivity,
                    object : CaptchaDialogHelper.OnDialogCallback {
                        override fun onResult(entity: CaptchaCheckResultEntity?) {
                            mViewModel.gainMessageCode(
                                RequestMessageCodeEntity(
                                    DataCompat.toString(
                                        phoneNumber
                                    ),
                                    DataCompat.toString(entity?.randstr),
                                    DataCompat.toString(entity?.ticket)
                                )
                            )
                        }

                    })
                //   mViewModel.gainMessageCode(RequestMessageCodeEntity(DataCompat.toString(phoneNumber)))

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
                    com.sbnh.comm.R.mipmap.icon_comm_check
                )
            } else {
                UICompat.setImageRes(
                    mViewBinding.aivCheckAgreement,
                    com.sbnh.comm.R.mipmap.icon_comm_normal
                )
            }
        }
        mViewBinding.atvRegister.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {

                val phoneNumber =
                  MetaViewCompat.getTextViewText(mViewBinding.aetPhone,true)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val messageCode = MetaViewCompat.getTextViewText(mViewBinding.aetGainCode)
                if (DataCompat.isEmpty(messageCode)) {
                    showToast(com.sbnh.comm.R.string.please_input_message_code)
                    return
                }
                if (!isCheckAgreement) {
                    showToast(com.sbnh.comm.R.string.please_agree_user_agreement)
                    return
                }
                val inviteCode = MetaViewCompat.getTextViewText(mViewBinding.aetInviteCode)

                mViewModel.register(
                    RequestRegisterEntity(
                        DataCompat.toString(phoneNumber),
                        DataCompat.toString(messageCode),
                        DataCompat.toString(inviteCode)
                    )
                )
            }

        })
        mViewBinding.clContent.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                MetaViewCompat.hideSoftKeyBoard(mViewBinding.clContent)
            }

        })

    }

    private val mPhoneNumberTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            UICompat.setPhoneEditText(mViewBinding.aetPhone,s,start, before)
        }

        override fun afterTextChanged(s: Editable?) {
        }

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
        mViewModel.mRegisterLiveData.observe(this) {
            lifecycleScope.launch {
                UserInfoStore.get().putEntity(it)
                ARoutersActivity.loginActivityComplete()
            }
        }

    }

    override fun resultGainMessageCode() {
        super.resultGainMessageCode()
        mViewModel.downTimer(Contract.MESSAGE_CODE_DOWN_TIME_LENGTH)
    }

    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        MetaViewCompat.showSoftKeyBoard(mViewBinding.aetPhone)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.aetPhone.removeTextChangedListener(mPhoneNumberTextWatcher)
    }
}