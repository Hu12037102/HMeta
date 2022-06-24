package com.sbnh.pay.dialog

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.pay.adapter.SelectorBankCardAdapter
import com.sbnh.pay.databinding.DialogSelectorBankCardViewBinding
import com.sbnh.pay.databinding.ItemFootSelectorBankCardViewBinding
import com.sbnh.pay.viewmodel.SelectorBankCardViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 10:04
 * 更新时间: 2022/6/24 10:04
 * 描述:
 */
@Route(path = ARouterConfig.Path.Pay.DIALOG_SELECTOR_BANK_CARD)
class SelectorBankCardDialog :
    BaseCompatDialog<DialogSelectorBankCardViewBinding, SelectorBankCardViewModel>() {
    private var mOnSelectorBankCardInfoCallback: OnSelectorBankCardInfoCallback? = null
    fun setOnSelectorBankCardInfoCallback(onSelectorBankCardInfoCallback: OnSelectorBankCardInfoCallback?) {
        this.mOnSelectorBankCardInfoCallback = onSelectorBankCardInfoCallback
    }

    private var mAdapter: SelectorBankCardAdapter? = null
    private val mFootViewBinding: ItemFootSelectorBankCardViewBinding by lazy {
        ItemFootSelectorBankCardViewBinding.inflate(
            layoutInflater, mViewBinding.rvData, false
        )
    }
    private val mData = ArrayList<BankCardEntity>()
    override fun getViewBinding(): DialogSelectorBankCardViewBinding =
        DialogSelectorBankCardViewBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<SelectorBankCardViewModel> =
        SelectorBankCardViewModel::class.java

    override fun initView() {
        mViewBinding.rvData.layoutManager = LinearLayoutManager(context)

    }


    override fun initData() {
        mAdapter = SelectorBankCardAdapter(requireContext(), mData)
        mAdapter?.addFootView(mFootViewBinding.root)
        mViewBinding.rvData.adapter = mAdapter


    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadBankCardList(RequestPagerListEntity())
    }

    override fun initEvent() {
        mFootViewBinding.root.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_ADD_BANK_CARD)
            }

        })
        mAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                mOnSelectorBankCardInfoCallback?.onResultBankCard(mData[position])
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mBankListLiveData.observe(this) {
            val entity = BaseEntity.getPagerData(it)
            mData.clear()
            if (entity != null) {
                mData.addAll(entity)
            }
            mAdapter?.notifyDataSetChanged()
        }
    }

    interface OnSelectorBankCardInfoCallback {
        fun onResultBankCard(entity: BankCardEntity)
    }

}