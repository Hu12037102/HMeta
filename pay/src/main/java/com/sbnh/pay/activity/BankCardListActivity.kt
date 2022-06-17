package com.sbnh.pay.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.entity.pay.BankCardEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.pay.adapter.BankCardListAdapter
import com.sbnh.pay.databinding.ActivityBankCardListBinding
import com.sbnh.pay.databinding.ItemFootBankCardListViewBinding
import com.sbnh.pay.databinding.ItemHeadBankCardListViewBinding
import com.sbnh.pay.viewmodel.BankCardListViewModel

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
    }

    override fun initEvent() {

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
                ARouters.startActivity(ARouterConfig.Path.Pay.ACTIVITY_ADD_BANK_CARD)
            }

        })
    }
}