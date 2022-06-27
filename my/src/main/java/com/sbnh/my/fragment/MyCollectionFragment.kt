package com.sbnh.my.fragment

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.huxiaobai.imp.OnRecyclerViewItemClickListener
import com.sbnh.comm.Contract
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.entity.base.BasePagerEntity
import com.sbnh.comm.entity.base.UserInfoEntity
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.entity.request.RequestPagerListEntity
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.MyCollectionListAdapter
import com.sbnh.my.databinding.FragmentCollectionBinding
import com.sbnh.my.viewmodel.MyCollectionViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout


/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/14 16:57
 * 更新时间: 2022/6/14 16:57
 * 描述:
 */
@Route(path = ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION)
class MyCollectionFragment :
    BaseCompatFragment<FragmentCollectionBinding, MyCollectionViewModel>() {

    private var mCollectionAdapter: MyCollectionListAdapter? = null
    private val mCollectionData = ArrayList<MyCollectionEntity>()

    override fun getViewBinding(): FragmentCollectionBinding =
        FragmentCollectionBinding.inflate(layoutInflater)

    override fun getViewModelClass(): Class<MyCollectionViewModel> =
        MyCollectionViewModel::class.java

    override fun initView() {
        context?.let { context ->
            mViewBinding.rvData.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL).apply {
                    setDrawable(GradientDrawable().apply {
                        setSize(PhoneCompat.dp2px(context, 13f), 0)
                    })
                }
            )
            mViewBinding.rvData.layoutManager =
                GridLayoutManager(context, 2)
        }

        MetaViewCompat.setClickButton(mViewBinding.atvLogin, Contract.DP.VALUE_8F)
        mEmptyLayout?.setText(DataCompat.getResString(com.sbnh.comm.R.string.no_collection))
    }

    override fun initData() {
        context?.let { mViewBinding.rvData.adapter = MyCollectionListAdapter(it, mCollectionData).also { adapter -> mCollectionAdapter = adapter } }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.loadUserInfo()
    }

    override fun initEvent() {
        mViewBinding.atvLogin.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARoutersActivity.startLoginActivity()
            }

        })
        mCollectionAdapter?.setOnRecyclerViewItemClickListener(object :
            OnRecyclerViewItemClickListener {
            override fun clickEmptyView(view: View) {
                TODO("Not yet implemented")
            }

            override fun clickItem(view: View, position: Int) {
                ARoutersActivity.startCollectionNumDetailsActivity(mCollectionData[position])
            }

            override fun longClickItem(view: View, position: Int) {
//                TODO("Not yet implemented")
            }

        })
    }

    override fun isLoadEmptyView(): Boolean = true

    override fun resultUserInfo(userInfoEntity: UserInfoEntity?) {
        if (!UserInfoStore.isLogin(userInfoEntity)) {
            mViewBinding.atvLogin.visibility = View.VISIBLE
            mViewBinding.refreshLayout.setEnableRefresh(false)
            mEmptyLayout?.visibility = View.GONE
            mCollectionData.clear()
            mCollectionAdapter?.notifyDataSetChanged()
        } else {
            mViewBinding.atvLogin.visibility = View.GONE
            mViewBinding.refreshLayout.setEnableRefresh(true)
            mViewModel.loadCachedCollectionList()
        }
    }

    override fun loadSmartData(refreshLayout: RefreshLayout?, isRefresh: Boolean) {
        mViewModel.loadCollectionList(RequestPagerListEntity(100))
    }

    override fun initObserve() {
        super.initObserve()
        // 本页面不需要加载更多
        mViewModel.mRefreshLiveData.removeObservers(this)
        mViewModel.mCollectionLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            // 缓存
            mViewModel.cacheCollectionList(data)
            mCollectionData.clear()
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionData.addAll(data!!)
                mEmptyLayout?.visibility = View.GONE
            } else {
                mEmptyLayout?.visibility = View.VISIBLE
            }
            mCollectionAdapter?.notifyDataSetChanged()
        }

        mViewModel.mCachedCollectionLiveData.observe(this) {
            val data = BasePagerEntity.getData(it)
            mCollectionData.clear()
            if (CollectionCompat.notEmptyList(data)) {
                mCollectionData.addAll(data!!)
                mCollectionAdapter?.notifyDataSetChanged()
                mEmptyLayout?.visibility = View.GONE
            } else {
                mViewBinding.refreshLayout.autoRefresh()
                mEmptyLayout?.visibility = View.VISIBLE
            }
        }
    }

    override fun isLoadLoadingView(): Boolean = false

}