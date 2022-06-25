package com.sbnh.healermeta.activity

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.fragment.BazaarFragment
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DialogCompat
import com.sbnh.comm.dialog.VersionUpdateDialog
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.entity.base.VersionEntity
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
        mViewBinding.rvTab.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mViewBinding.vpContent.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mLoadingViewBinding?.cpbLoading?.hide()
    }

    override fun initData() {
        mViewModel.loadAppVersion()
        initBottomView()
        initPagerAdapter()

    }

    private fun initBottomView() {
        mTabData.addAll(mViewModel.createBottomTabs())
        mTabAdapter = MainTabAdapter(this, mTabData)
        mViewBinding.rvTab.adapter = mTabAdapter
    }

    private fun initPagerAdapter() {
        val homeFragment =
            ARouters.getFragment(ARouterConfig.Path.Home.FRAGMENT_HOME)

        Log.w("initPagerAdapter--", "$homeFragment")
        val bazaarFragment =
            ARouters.getFragment(ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR)
        val myFragment =
            ARouters.getFragment(ARouterConfig.Path.My.FRAGMENT_MY)
        if (homeFragment is Fragment) {
            mFragments.add(homeFragment)
        }
        if (bazaarFragment is Fragment) {
            mFragments.add(bazaarFragment)
        }
        if (myFragment is Fragment) {
            mFragments.add(myFragment)
        }
        val pagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = CollectionCompat.getListSize(mFragments)

            override fun createFragment(position: Int): Fragment = mFragments[position]
        }
        mViewBinding.vpContent.let {
            it.offscreenPageLimit = 1
            it.isUserInputEnabled = false
            it.adapter = pagerAdapter
        }
    }

    override fun initEvent() {
        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {
                mViewBinding.vpContent.setCurrentItem(position, false)
            }

        })
        mViewBinding.vpContent.registerOnPageChangeCallback(mPagerCallback)
    }

    private val mPagerCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            mTabAdapter?.selectorTab(position)
        }


    }

    override fun initObserve() {
        super.initObserve()
        mViewModel.mVersionLiveData.observe(this) {
            val versionUpdateDialog =
                ARouters.build(ARouterConfig.Path.Comm.DIALOG_VERSION_UPDATE)
                    .withParcelable(ARouterConfig.Key.PARCELABLE, it)
                    .navigation()
            if (versionUpdateDialog is VersionUpdateDialog) {
                DialogCompat.showFragmentDialog(versionUpdateDialog, supportFragmentManager)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.vpContent.unregisterOnPageChangeCallback(mPagerCallback)
    }


}