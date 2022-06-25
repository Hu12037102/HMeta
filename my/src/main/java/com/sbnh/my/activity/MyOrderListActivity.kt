package com.sbnh.my.activity

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.entity.order.OrderTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.my.adapter.MyOrderTabAdapter
import com.sbnh.my.databinding.ActivityMyOrderListBinding
import com.sbnh.my.viewmodel.MyOrderListViewModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/21 19:36
 * 更新时间: 2022/6/21 19:36
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.ACTIVITY_MY_ORDER_LIST)
class MyOrderListActivity : BaseCompatActivity<ActivityMyOrderListBinding, MyOrderListViewModel>() {
    private val mTabData = ArrayList<OrderTabEntity>()
    private var mTabAdapter: MyOrderTabAdapter? = null
    private val mFragments = ArrayList<Fragment>()

    override fun getViewBinding(): ActivityMyOrderListBinding =
        ActivityMyOrderListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyOrderListViewModel> = MyOrderListViewModel::class.java

    override fun initView() {
        mViewBinding.rvTab.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.vpContent.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mViewBinding.vpContent.offscreenPageLimit = 1
    }

    override fun initData() {
        initTabAdapter()
        initPagerAdapter()
    }


    private fun initTabAdapter() {
        mTabData.addAll(mViewModel.createOrderTab())
        mTabAdapter = MyOrderTabAdapter(this, mTabData)
        mViewBinding.rvTab.adapter = mTabAdapter

    }

    private fun initPagerAdapter() {
        for (entity in mTabData) {
            val postcard: Postcard = ARouters.build(ARouterConfig.Path.My.FRAGMENT_MY_ORDER_CONTENT)
            entity.id?.let {
                postcard.withInt(ARouterConfig.Key.ID, it)
            }
            val any = postcard.navigation();
            if (any is Fragment) {
                mFragments.add(any)
            }
        }
        val fragmentStatusAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = CollectionCompat.getListSize(mFragments)

            override fun createFragment(position: Int): Fragment = mFragments[position]

        }
        mViewBinding.vpContent.adapter = fragmentStatusAdapter
    }

    override fun initEvent() {
        mViewBinding.vpContent.registerOnPageChangeCallback(mPagerCallback)
        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                mViewBinding.vpContent.currentItem = position
            }

        })
    }

    private val mPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            mTabAdapter?.selectorTab(position)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.vpContent.unregisterOnPageChangeCallback(mPagerCallback)
    }
}