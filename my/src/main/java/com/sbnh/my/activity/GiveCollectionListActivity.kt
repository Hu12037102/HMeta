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
import com.sbnh.comm.entity.my.GiveCollectionTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.my.adapter.GiveCollectionTabAdapter
import com.sbnh.my.databinding.ActivityGiveCollectionListBinding
import com.sbnh.my.viewmodel.GiveCollectionListViewModel

@Route(path = ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION_LIST)
class GiveCollectionListActivity : BaseCompatActivity<ActivityGiveCollectionListBinding, GiveCollectionListViewModel>() {
    private val mTabData = ArrayList<GiveCollectionTabEntity>()
    private var mTabAdapter: GiveCollectionTabAdapter? = null
    private val mFragments = ArrayList<Fragment>()

    override fun getViewBinding(): ActivityGiveCollectionListBinding =
        ActivityGiveCollectionListBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<GiveCollectionListViewModel> = GiveCollectionListViewModel::class.java

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
        mTabData.addAll(mViewModel.createTab())
        mTabAdapter = GiveCollectionTabAdapter(this, mTabData)
        mViewBinding.rvTab.adapter = mTabAdapter

    }

    private fun initPagerAdapter() {
        for (entity in mTabData) {
            val postcard: Postcard = ARouters.build(ARouterConfig.Path.My.FRAGMENT_GIVE_COLLECTION_CONTENT)
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