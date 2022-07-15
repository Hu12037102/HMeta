package com.sbnh.my.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.request.RequestGiveCollectionEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.databinding.ActivityGiveCollectionBinding
import com.sbnh.my.viewmodel.GiveCollectionViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 15:32
 * 更新时间: 2022/6/17 15:32
 * 描述:转赠藏品
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION)
class GiveCollectionActivity :
    BaseCompatActivity<ActivityGiveCollectionBinding, GiveCollectionViewModel>() {

    private var mCollectionNumDetailsId: String? = null
    private var mMerchandiseId: String? = null

    private var isAgreeAgreement = false
    override fun getViewBinding(): ActivityGiveCollectionBinding =
        ActivityGiveCollectionBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<GiveCollectionViewModel> =
        GiveCollectionViewModel::class.java

    override fun initView() {
        mMerchandiseId = intent.getStringExtra(ARouterConfig.Key.ID)
        mCollectionNumDetailsId = intent.getStringExtra(ARouterConfig.Key.OTHER_ID)
    }

    override fun onWindowFirstFocusChanged(hasFocus: Boolean) {
        super.onWindowFirstFocusChanged(hasFocus)
        MetaViewCompat.showSoftKeyBoard(mViewBinding.aetPhoneNumber)
    }

    override fun initData() {

    }

    override fun initEvent() {
        mViewBinding.aivCheck.setOnClickListener {
            isAgreeAgreement = !isAgreeAgreement
            UICompat.setImageRes(
                mViewBinding.aivCheck,
                if (isAgreeAgreement) com.sbnh.comm.R.mipmap.icon_comm_check else com.sbnh.comm.R.mipmap.icon_comm_normal
            )
        }
        mViewBinding.atvSureGive.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                val phoneNumber = MetaViewCompat.getTextViewText(mViewBinding.aetPhoneNumber)
                if (!NumberCompat.isPhoneNumber(phoneNumber)) {
                    showToast(com.sbnh.comm.R.string.please_input_sure_phone_number)
                    return
                }
                val password = MetaViewCompat.getTextViewText(mViewBinding.aetPaymentPassword)
                if (!NumberCompat.isPayPassword(password)) {
                    showToast(com.sbnh.comm.R.string.please_six_length_password)
                    return
                }
                if (!isAgreeAgreement) {
                    showToast(com.sbnh.comm.R.string.please_agree_user_agreement)
                    return
                }

                mViewModel.giveCollection(RequestGiveCollectionEntity(phoneNumber.toString(), mCollectionNumDetailsId?: "", password.toString()))
            }

        })
        mViewBinding.root.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                MetaViewCompat.hideSoftKeyBoard(v)
            }

        })

    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mGiveCollectionLiveData.observe(this) {
            showToast(com.sbnh.comm.R.string.give_collection_succeed)
            mViewModel.unionUpdateCachedMyCollection(mCollectionNumDetailsId?: "", mMerchandiseId?: "")
        }

        mViewModel.mUnionUpdatedLiveData.observe(this) {
            finish()
        }
    }

}