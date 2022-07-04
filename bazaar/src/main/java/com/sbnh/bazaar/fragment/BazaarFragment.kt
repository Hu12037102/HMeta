package com.sbnh.bazaar.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.bazaar.databinding.FragmentBazaarBinding
import com.sbnh.bazaar.viewmodel.BazaarViewModel
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters

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

    override fun getViewModelClass(): Class<BazaarViewModel> = BazaarViewModel::class.java

    override fun initView() {
        mEmptyLayout?.show()
        mEmptyLayout?.setText(DataCompat.getResString(com.sbnh.comm.R.string.bazaar_not_open))
    }

    override fun initData() {
        initPager()
    }

    private fun initPager() {
        val fragment = ARouters.getFragment((ARouterConfig.Path.Bazaar.FRAGMENT_BAZAAR_CONTENT))
        if (fragment is BazaarContentFragment) {
            mFragments.add(fragment)
            val fragmentAdapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int = CollectionCompat.getListSize(mFragments)

                override fun createFragment(position: Int): Fragment = mFragments[position]

            }
            mViewBinding.vpContent.adapter = fragmentAdapter

        }

    }

    override fun initEvent() {
    }


    override fun isLoadEmptyView(): Boolean = true
}