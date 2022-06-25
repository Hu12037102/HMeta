package com.sbnh.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbnh.comm.databinding.ItemHomeBannerViewBinding
import com.sbnh.comm.entity.home.HomeBannerEntity
import com.sbnh.comm.other.glide.GlideCompat
import com.youth.banner.adapter.BannerAdapter

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/24 10:22
 * 更新时间: 2022/6/24 10:22
 * 描述:
 */
class HomeBannerAdapter(private val context: Context, data: List<HomeBannerEntity>) :
    BannerAdapter<HomeBannerEntity, HomeBannerAdapter.ViewHolder>(data) {
    class ViewHolder(val viewBinding: ItemHomeBannerViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ViewHolder = ViewHolder(
        ItemHomeBannerViewBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindView(
        holder: ViewHolder?,
        data: HomeBannerEntity?,
        position: Int,
        size: Int
    ) {
        GlideCompat.loadImage(data?.resource, viewHolder?.viewBinding?.aivContent)

    }

}