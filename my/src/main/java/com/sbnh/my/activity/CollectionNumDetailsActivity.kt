package com.sbnh.my.activity

import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.sbnh.comm.base.activity.BaseActivity
import com.sbnh.comm.base.activity.BaseCompatActivity
import com.sbnh.comm.compat.MetaViewCompat
import com.sbnh.comm.compat.NumberCompat
import com.sbnh.comm.compat.PhoneCompat
import com.sbnh.comm.entity.my.MyCollectionEntity
import com.sbnh.comm.other.arouter.ARouterConfig
import com.sbnh.comm.other.arouter.ARouters
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.my.fragment.CollectionNumDetailsFragment
import com.sbnh.my.fragment.MyCollectionFragment

@Route(path = ARouterConfig.Path.My.ACTIVITY_COLLECTION_NUM_DETAILS)
class CollectionNumDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView: View = findViewById(android.R.id.content)
        contentView.setBackgroundColor(ContextCompat.getColor(this, com.sbnh.comm.R.color.color99000000))
        contentView.setPadding(0, resources.getDimensionPixelSize(com.sbnh.comm.R.dimen.size_146dp), 0, 0)

        overridePendingTransition(com.sbnh.comm.R.anim.anim_bottom_to_center, android.R.anim.fade_out)

        val fragment: CollectionNumDetailsFragment =
            ARouters.build(ARouterConfig.Path.My.FRAGMENT_COLLECTION_NUM_DETAILS)
                .navigation() as CollectionNumDetailsFragment
        fragment.arguments = Bundle().apply {
            putParcelable(ARouterConfig.Key.MY_COLLECTION, intent.getParcelableExtra(ARouterConfig.Key.MY_COLLECTION))
        }
        supportFragmentManager.beginTransaction().add(android.R.id.content, fragment).commit()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, com.sbnh.comm.R.anim.anim_center_to_bottom)
    }

}