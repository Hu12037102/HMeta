package com.sbnh.pay.dialog

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.adapter.BaseRecyclerAdapter
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.dialog.BaseCompatDialog
import com.sbnh.comm.base.dialog.BaseDataDialog
import com.sbnh.comm.entity.base.BaseEntity
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.utils.LogUtils
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
    BaseDataDialog<DialogSelectorBankCardViewBinding, SelectorBankCardViewModel>() {
    private var mIntentEntity: BankCardEntity? = null
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
        mIntentEntity = arguments?.getParcelable<BankCardEntity>(ARouterConfig.Key.PARCELABLE)
        LogUtils.w(
            "SelectorBankCardDialog--", "$mIntentEntity"
        )
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
        mAdapter?.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
            }

            override fun clickItem(view: View, position: Int) {
                mOnCallbackValues?.onValue(mData[position])
            }

            override fun longClickItem(view: View, position: Int) {
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
            for (child in mData) {
                if (TextUtils.equals(child.id, mIntentEntity?.id)) {
                    child.isCheck = true
                    break
                }
            }
            mAdapter?.notifyDataSetChanged()
        }
    }


}