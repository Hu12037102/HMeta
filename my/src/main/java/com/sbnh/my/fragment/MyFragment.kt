package com.sbnh.my.fragment

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.core.net.ConnectivityManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.callback.OnRecyclerItemClickListener
import com.sbnh.comm.base.fragment.BaseCompatFragment
import com.sbnh.comm.base.interfaces.OnDialogItemInfoClickListener
import com.sbnh.comm.compat.*
import com.sbnh.comm.dialog.CompoundCollectionPreviewDialog
import com.sbnh.comm.dialog.RealNameDialog
import com.sbnh.comm.entity.base.*
import com.sbnh.comm.http.IApiService
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.GlideCompat
import com.sbnh.comm.utils.LogUtils
import com.sbnh.comm.weight.click.DelayedClick
import com.sbnh.my.adapter.MyTabAdapter
import com.sbnh.my.databinding.FragmentMyBinding
import com.sbnh.my.viewmodel.MyViewModel
import kotlinx.coroutines.launch

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
        //    mViewModel.loadUserInfo()
        iniTabAdapter()
        initPager()

    }

    private fun initPager() {
        val fragment: MyCollectionFragment =
            ARouters.build(ARouterConfig.Path.My.FRAGMENT_MY_COLLECTION)
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
        mViewBinding.aivSetting.setOnClickListener(object : DelayedClick() {
            override fun onDelayedClick(v: View?) {
                ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_SETTING)
            }

        })

        mTabAdapter?.setOnRecyclerItemClickListener(object : OnRecyclerItemClickListener {
            override fun onClickItem(view: View?, position: Int) {

                when (mTabData[position].id) {
                    TAB_ORDER -> {
                        ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_MY_ORDER_LIST)
                    }
                    TAB_OFFICIAL_ACCOUNTS -> {
                        ARoutersActivity.startPictureSaveActivity(IApiService.H5.OFFICIAL_ACCOUNTS)
                    }
                    TAB_INVITE_FRIEND -> {
                        lifecycleScope.launch {
                            ARoutersActivity.startWebContentActivity(
                                WebViewCompat.appendUrl(
                                    IApiService.H5.INVITE_FRIEND, IApiService.Key.SID,
                                    UserInfoStore.get().getSid()
                                )
                            )
                        }
                    }
                    TAB_DONATION -> {
                        ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_GIVE_COLLECTION_LIST)
                    }
                    TAB_MY_WALLET->{
                        ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_MY_WALLET)
                    }
                    TAB_LOTTERY_HELP -> {
                        lifecycleScope.launch {
                            val sid = UserInfoStore.get().getSid()
                            val url = WebViewCompat.appendUrl(
                                IApiService.H5.LOTTERY,
                                IApiService.Key.SID,
                                sid
                            )
                            ARoutersActivity.startWebContentActivity(url)
                        }

                    }
                    TAB_COMPOUND -> {
                        //  ARouters.startActivity(ARouterConfig.Path.My.ACTIVITY_COMPOUND_DETAILED_LIST)
                        val intent = ARouters.intent(
                            context,
                            ARouterConfig.Path.My.ACTIVITY_COMPOUND_DETAILED_LIST
                        )
                        startActivityForResult(intent)
                    }
                    else -> {
                        showToast(com.sbnh.comm.R.string.the_function_is_not_available)

                    }
                }
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
            UICompat.setImageRes(
                mViewBinding.aivRealNameStatus,
                if (userInfoEntity?.hasRealName == true) com.sbnh.comm.R.mipmap.icon_my_have_real_name else com.sbnh.comm.R.mipmap.icon_my_not_real_name
            )
            mViewBinding.aivRealNameStatus.setOnClickListener(object : DelayedClick() {
                override fun onDelayedClick(v: View?) {
                    if (userInfoEntity?.hasRealName == false) {
                        val dialog = RealNameDialog()
                        DialogCompat.showFragmentDialog(dialog, childFragmentManager)
                        dialog.setOnDialogItemInfoClickListener(object :
                            OnDialogItemInfoClickListener {
                            override fun onClickConfirm(view: View?) {
                                mViewModel.loadUserInfo()
                                dialog.dismiss()
                            }

                        })
                    }

                }

            })

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

    }

    override fun onActivityResultCallback(result: ActivityResult) {
        super.onActivityResultCallback(result)
        if (result.resultCode == Activity.RESULT_OK) {
            for (fragment in mFragments) {
                if (fragment is MyCollectionFragment) {
                    try {
                        fragment.loadSmartData()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}