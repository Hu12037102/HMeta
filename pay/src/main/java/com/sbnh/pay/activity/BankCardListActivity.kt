package com.sbnh.pay.activity

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.DialogCompat
import com.sbnh.comm.dialog.BottomItemDialog
import com.sbnh.comm.dialog.SetPaymentPasswordDialog
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.base.BasePagerEntity2
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.pay.RequestUnbindBankCardEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.pay.adapter.BankCardListAdapter
import com.sbnh.pay.databinding.ActivityBankCardListBinding
import com.sbnh.pay.databinding.ItemFootBankCardListViewBinding
import com.sbnh.pay.databinding.ItemHeadBankCardListViewBinding
import com.sbnh.pay.viewmodel.BankCardListViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/17 9:49
 * 更新时间: 2022/6/17 9:49
 * 描述:
 */
@Route(path = ARouterConfig.Path.Pay.ACTIVITY_BANK_CARD_LIST)
class BankCardListActivity :
    BaseCompatActivity<ActivityBankCardListBinding, BankCardListViewModel>() {
    private var mAdapter: BankCardListAdapter? = null
    private val mData = ArrayList<BankCardEntity>()
    private val mHeadViewBinding: ItemHeadBankCardListViewBinding by lazy {
        ItemHeadBankCardListViewBinding.inflate(
            layoutInflater, mViewBinding.rvData, false
        )
    }
    private val mFootViewBinding: ItemFootBankCardListViewBinding by lazy {
        ItemFootBankCardListViewBinding.inflate(
            layoutInflater,
            mViewBinding.rvData,
            false
        )
    }

    override fun getViewBinding(): ActivityBankCardListBinding =
        ActivityBankCardListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<BankCardListViewModel> =
        BankCardListViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(this)
        initHeadView()
        initFootView()
    }

    override fun initData() {
        mAdapter = BankCardListAdapter(this, mData)
        mAdapter?.addHeadView(mHeadViewBinding.root)
        mAdapter?.addFootView(mFootViewBinding.root)
        mViewBinding.rvData.adapter = mAdapter
        loadSmartData()
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mViewModel.loadBankCardList(RequestPagerListEntity())
    }

    override fun initEvent() {
        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
            }

            override fun longClickItem(view: View, position: Int) {
            }

        })
        mAdapter?.setOnClickMoreViewListener(object : BankCardListAdapter.OnClickMoreViewListener {
            override fun clickMore(view: View?, position: Int) {
                showUnbindBankCardDialog(mData[position])
            }

        })

    }

    private fun showUnbindBankCardDialog(entity: BankCardEntity) {
        val dialog =
            BottomItemDialog(DataCompat.getResString(com.sbnh.comm.R.string.unbind))
        DialogCompat.showFragmentDialog(dialog, supportFragmentManager)
        dialog.setOnDialogItemInfoClickListener(object : OnDialogItemInfoClickListener {
            override fun onClickConfirm(view: View?) {
                showPaymentPasswordDialog(entity)
                dialog.dismiss()
            }

            override fun onClickCancel(view: View?) {
                dialog.dismiss()
            }

        })
    }

    private fun showPaymentPasswordDialog(entity: BankCardEntity) {
        val dialog = SetPaymentPasswordDialog(
            DataCompat.getResString(com.sbnh.comm.R.string.untying_bank_card),
            DataCompat.getResString(com.sbnh.comm.R.string.please_inout_payment_password_check_identity)
        )
        DialogCompat.showFragmentDialog(dialog, supportFragmentManager)
        dialog.setOnInputPasswordCallback(object :
            SetPaymentPasswordDialog.OnInputPasswordCallback {
            override fun onComplete(password: String) {
                mViewModel.unbindBanCard(
                    RequestUnbindBankCardEntity(
                        DataCompat.toString(entity.id),
                        password
                    )
                )
                dialog.dismiss()
            }

        })
    }

    private fun initHeadView() {
        GlideCompat.loadWarpImage(
            com.sbnh.comm.R.mipmap.icon_pay_bank_card_normal,
            mHeadViewBinding.aivHeadContent
        )

    }

    private fun initFootView() {
        GlideCompat.loadWarpImage(
            com.sbnh.comm.R.mipmap.icon_pay_bank_card_add,
            mFootViewBinding.aivFootContent
        )
        mFootViewBinding.aivFootContent.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                startActivityForResult(
                    Intent(
                        this@BankCardListActivity,
                        AddBankCardActivity::class.java
                    )
                )
                //  ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_ADD_BANK_CARD)
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mBankListLiveData.observe(this) {
            mViewBinding.refreshLayout.setEnableLoadMore(false)
            val data = BaseEntity.getPagerData(it)

            if (mViewModel.isRefresh) {
                mData.clear()
            }
            if (CollectionCompat.notEmptyList(data)) {
                mData.addAll(data!!)
            }
            if (CollectionCompat.notEmptyList(mData)) {
                mAdapter?.removeHeadView()
            }
            mAdapter?.notifyDataSetChanged()
        }
        mViewModel.mUnbindBankCardLiveData.observe(this) {
            val data = BaseEntity.getData(it)
            val iterator = mData.iterator()
            while (iterator.hasNext()) {
                val entity = iterator.next()
                if (TextUtils.equals(entity.id, data)) {
                    iterator.remove()
                }
            }
            if (CollectionCompat.isEmptyList(mData)) {
                mAdapter?.addHeadView(mHeadViewBinding.root)
            }
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun onActivityResultCallback(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            loadSmartData()
        }
    }
}