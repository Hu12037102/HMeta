package com.sbnh.bazaar.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.adapter.BazaarTabAdapter
import com.sbnh.bazaar.databinding.FragmentBazaarBinding
import com.sbnh.bazaar.viewmodel.BazaarViewModel
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.bazaar.BazaarTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.weight.click.DelayedClick

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 16:38
 * 更新时间: 2022/6/13 16:38
 * 描述:
 */
@Route(path = ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR)
class BazaarFragment : BaseCompatFragment<FragmentBazaarBinding, BazaarViewModel>() {
    private val mFragments = ArrayList<BazaarContentFragment>()
    override fun getViewBinding(): FragmentBazaarBinding =
        FragmentBazaarBinding.inflate(layoutInflater)

    private val mTabData = ArrayList<BazaarTabEntity>()
    private var mTabAdapter: BazaarTabAdapter? = null

    override fun getViewModelClass(): Class<BazaarViewModel> = BazaarViewModel::class.java

    override fun initView() {
        mViewBinding.rvTab.layoutManager = LinearLayoutManager(
            DataCompat.checkContext(context),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun initData() {
        mTabAdapter = BazaarTabAdapter(DataCompat.checkContext(context), mTabData)
        mViewBinding.rvTab.adapter = mTabAdapter
        mViewModel.loadTabs()
    }


    private fun initPager() {
        if (mViewBinding.vpContent.adapter == null) {
            for (entity in mTabData) {
                val fragment = ARouters.build(ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_CONTENT)
                    .withString(ARouterConfig.Key.ID, entity.id)
                    .navigation()
                if (fragment is BazaarContentFragment) {
                    mFragments.add(fragment)
                }

            }
            val fragmentAdapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int = CollectionCompat.getListSize(mFragments)

                override fun createFragment(position: Int): Fragment = mFragments[position]
            }
            mViewBinding.vpContent.adapter = fragmentAdapter
        } else {
            mViewBinding.vpContent.adapter?.notifyDataSetChanged()
        }


    }

    override fun initEvent() {
        mViewBinding.vpContent.registerOnPageChangeCallback(mOnPageChangeCallback)
        mEmptyLayout?.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                mViewModel.loadTabs()
            }

        })
        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                mViewBinding.vpContent.currentItem = position
            }

        })
    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mTabLiveData.observe(this) {
            mTabData.clear()
            CollectionCompat.addAll(mTabData, it)
            mTabAdapter?.notifyDataSetChanged()
            initPager()

        }
    }

    private val mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            mTabAdapter?.selectorTab(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.vpContent.unregisterOnPageChangeCallback(mOnPageChangeCallback)
    }
}