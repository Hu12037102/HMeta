package com.sbnh.healermeta.activity

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.activity.BazaarFragment
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.healermeta.adapter.MainTabAdapter
import com.sbnh.healermeta.databinding.ActivityMainBinding
import com.sbnh.healermeta.viewmodel.MainViewModel
import com.sbnh.home.fragment.HomeFragment
import com.sbnh.my.fragment.MyFragment

@Route(path = ARouterConfig.Path.Main.ACTIVITY_MAIN)
class MainActivity : BaseCompatActivity<ActivityMainBinding, MainViewModel>() {
    private val mTabData: ArrayList<SelectorTabEntity> = ArrayList()
    private val mFragments: ArrayList<Fragment> = ArrayList()
    private var mTabAdapter: MainTabAdapter? = null
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun initView() {
        mViewBinding?.rvTab?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding?.vpContent?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mLoadingViewBinding?.cpbLoading?.hide()
    }

    override fun initData() {
        initBottomView()
        initPagerAdapter()

    }

    private fun initBottomView() {
        mViewModel?.let {
            mTabData.addAll(it.createBottomTabs())
        }
        mTabAdapter = MainTabAdapter(this, mTabData)
        mViewBinding?.rvTab?.adapter = mTabAdapter
    }

    private fun initPagerAdapter() {
        val homeFragment =
            ARouters.getFragment(ARouterConfig.Path.Home.FRAGMENT_HOME) as HomeFragment
        Log.w("initPagerAdapter--", "$homeFragment")
        val bazaarFragment =
            ARouters.getFragment(ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR) as BazaarFragment
        val myFragment =
            ARouters.getFragment(ARouterConfig.Path.My.FRAGMENT_MY) as MyFragment
        mFragments.add(homeFragment)
        mFragments.add(bazaarFragment)
        mFragments.add(myFragment)

        val pagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = CollectionCompat.getListSize(mFragments)

            override fun createFragment(position: Int): Fragment = mFragments[position]
        }
        mViewBinding?.vpContent?.let {
            it.offscreenPageLimit = mFragments.size
            it.isUserInputEnabled = false
            it.adapter = pagerAdapter
        }
    }

    override fun initEvent() {
        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View, position: Int) {
                mViewBinding?.vpContent?.setCurrentItem(position, false)
            }

        })
        mViewBinding?.vpContent?.registerOnPageChangeCallback(mPagerCallback)
    }

    private val mPagerCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            mTabAdapter?.selectorTab(position)
        }


    }

    override fun initObserve() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding?.vpContent?.unregisterOnPageChangeCallback(mPagerCallback)
    }


}