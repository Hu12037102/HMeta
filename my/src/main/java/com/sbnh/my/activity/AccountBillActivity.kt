package com.sbnh.my.activity

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.my.adapter.AccountBillTabAdapter
import com.sbnh.my.databinding.ActivityAccountBillBinding
import com.sbnh.my.fragment.AccountBillContentFragment
import com.sbnh.my.viewmodel.AccountBillViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/7/14 17:21
 * 更新时间: 2022/7/14 17:21
 * 描述:账单明细
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_ACCOUNT_BILL)
class AccountBillActivity : BaseCompatActivity<ActivityAccountBillBinding, AccountBillViewModel>() {
    private var mAccountBillAdapter: AccountBillTabAdapter? = null
    private val mTabData: List<SelectorTabEntity> by lazy { mViewModel.getTabs() }
    private val mFragments = ArrayList<AccountBillContentFragment>()

    override fun getViewBinding(): ActivityAccountBillBinding =
        ActivityAccountBillBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<AccountBillViewModel> = AccountBillViewModel::class.java

    override fun initView() {
        mViewBinding.rvTab.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.vpContent.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    override fun initData() {
        mAccountBillAdapter = AccountBillTabAdapter(this, mTabData)
        mViewBinding.rvTab.adapter = mAccountBillAdapter
        initPager()
    }

    private fun initPager() {
        for (i in mTabData.indices) {
            val fragment = ARouters.build(ARouterConfig.Path.My.FRAGMENT_ACCOUNT_BILL_CONTENT)
                .withInt(
                    ARouterConfig.Key.TYPE,
                    NumberCompat.checkInt(mTabData[i].type, SelectorTabEntity.AccountBill.TYPE_ALL)
                )
                .navigation()
            if (fragment is AccountBillContentFragment) {
                mFragments.add(fragment)
            }
        }
        val fragmentPagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = mFragments.size

            override fun createFragment(position: Int): Fragment = mFragments[position]
        }
        mViewBinding.vpContent.offscreenPageLimit = mFragments.size
        mViewBinding.vpContent.adapter = fragmentPagerAdapter

    }

    override fun initEvent() {
        mAccountBillAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                mViewBinding.vpContent.currentItem = position
            }

        })
        mViewBinding.vpContent.registerOnPageChangeCallback(mViewPagerCallback)
    }

    private val mViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mAccountBillAdapter?.selectorTab(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.vpContent.unregisterOnPageChangeCallback(mViewPagerCallback)
    }
}