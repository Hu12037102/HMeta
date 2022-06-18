package com.sbnh.my.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.UICompat
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.MyTabAdapter
import com.sbnh.my.databinding.FragmentMyBinding
import com.sbnh.my.viewmodel.MyViewModel

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/13 15:42
 * 更新时间: 2022/6/13 15:42
 * 描述:我的fragment
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY)
class MyFragment : BaseCompatFragment<FragmentMyBinding, MyViewModel>() {
    private val mTabData by lazy { mViewModel.createTabs() }
    private var mTabAdapter: MyTabAdapter? = null
    private val mLineCount = 4
    private val mFragments: ArrayList<Fragment> = ArrayList()
    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun initView() {
        mViewBinding.rvTab.layoutManager = GridLayoutManager(context, mLineCount)
        mViewBinding.vpContent.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }


    override fun initData() {
        mViewModel.loadUserInfo()
        iniTabAdapter()

    }

    private fun initPager(isLogin: Boolean) {
        val fragment: MyCollectionFragment =
            ARouters.build(ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION)
                .withBoolean(ARouterConfig.Key.HAS_LOGIN, isLogin)
                .navigation() as MyCollectionFragment
        mFragments.add(fragment)
        val pagerAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = CollectionCompat.getListSize(mFragments)

            override fun createFragment(position: Int): Fragment = mFragments[position]
        }
        mViewBinding.vpContent.apply {
            this.isUserInputEnabled = false
            this.offscreenPageLimit = 1
            this.adapter = pagerAdapter

        }
    }

    private fun iniTabAdapter() {
        mTabAdapter = MyTabAdapter(DataCompat.checkContext(context), mTabData, mLineCount)
        mViewBinding.rvTab.adapter = mTabAdapter
    }

    override fun initEvent() {
        mViewBinding.civHead.setOnClickListener {

        }
        mViewBinding.aivSetting.setOnClickListener(object :DelayedClick(){
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_SETTING)
            }

        })

    }

    override fun initObserve() {
        super.initObserve()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadUserInfo()
    }

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        val isLogin = UserInfoStore.isLogin(userInfoEntity)
        if (isLogin) {
            mViewBinding.clLogin.visibility = View.VISIBLE
            mViewBinding.clNotLogin.visibility = View.GONE
            GlideCompat.loadImage(userInfoEntity?.header, mViewBinding.civHead)
            UICompat.setText(mViewBinding.atvName, userInfoEntity?.nickName)
        } else {
            mViewBinding.clNotLogin.visibility = View.VISIBLE
            mViewBinding.clLogin.visibility = View.GONE
            GlideCompat.loadImage(com.sbnh.comm.R.mipmap.icon_my_not_login, mViewBinding.civHead)
            UICompat.setText(
                mViewBinding.atvNotLoginTitle,
                DataCompat.getResString(com.sbnh.comm.R.string.not_login)
            )
            UICompat.setText(
                mViewBinding.atvNotLoginDesc,
                DataCompat.getResString(com.sbnh.comm.R.string.login_read_you_digital_collection)
            )
        }
        initPager(isLogin)
    }

}