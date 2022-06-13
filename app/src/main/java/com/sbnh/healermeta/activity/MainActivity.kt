package com.sbnh.healermeta.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.entity.base.SelectorTabEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.healermeta.adapter.MainTabAdapter
import com.sbnh.healermeta.databinding.ActivityMainBinding
import com.sbnh.healermeta.viewmodel.MainViewModel

@Route(path = ARouterConfig.Path.Main.ACTIVITY_MAIN)
class MainActivity : BaseCompatActivity<ActivityMainBinding, MainViewModel>() {
    private val mTabData: ArrayList<SelectorTabEntity> = ArrayList()
    private var mTabAdapter: MainTabAdapter? = null
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun initView() {
        mViewBinding?.rvTab?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData() {
        initBottomView()


    }

    private fun initBottomView() {
        mViewModel?.let {
            mTabData.addAll(it.createBottomTabs())
        }
        mTabAdapter = MainTabAdapter(this, mTabData)
        mViewBinding?.rvTab?.adapter = mTabAdapter
    }

    override fun initEvent() {
        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View, position: Int) {

            }

        })
    }

    override fun initObserve() {

    }


}