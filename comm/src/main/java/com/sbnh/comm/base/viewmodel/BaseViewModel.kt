package com.sbnh.comm.base.viewmodel

import android.content.ContentValues
import android.provider.MediaStore
import androidx.annotation.IntDef
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.huxiaobai.compress.CompressGlide
import com.huxiaobai.compress.imp.OnCompressGlideImageCallback
import com.sbnh.comm.Contract
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.entity.base.*
import com.sbnh.comm.entity.order.RefreshStatusEntity
import com.sbnh.comm.entity.request.RequestMessageCodeEntity
import com.sbnh.comm.http.BaseService
import com.sbnh.comm.entity.base.ErrorResponse
import com.sbnh.comm.http.IApiService
import com.sbnh.comm.http.RetrofitManger
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.other.arouter.ARoutersActivity
import com.sbnh.comm.other.glide.HealerMetaGlide
import com.sbnh.comm.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.OutputStream

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 11:42
 * 更新时间: 2022/6/10 11:42
 * 描述:
 */
open class BaseViewModel : ViewModel() {

    companion object {
        const val TAG = "BaseViewModel"
        const val STATUS_LOGIN_OUT = 1
        const val STATUE_REQUEST_END = 2
        const val STATUE_HTTP_ERROR = 3
        const val STATUS_SHOW_EMPTY_VIEW = 4
        const val STATUS_HIND_EMPTY_VIEW = 5
    }

    @IntDef(
        STATUS_LOGIN_OUT,
        STATUE_REQUEST_END,
        STATUE_HTTP_ERROR,
        STATUS_SHOW_EMPTY_VIEW,
        STATUS_HIND_EMPTY_VIEW
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ViewModelStatus

    var mPagerNum = Contract.PAGE_NUM
    val mPagerSize = Contract.PAGE_SIZE
    var isRefresh = true
    var mLastTimestamp = System.currentTimeMillis()
    var mLastTime = System.currentTimeMillis()
    protected val mRetrofitManger: RetrofitManger by lazy { RetrofitManger.Instance }
    val mToastLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val mUserInfoLiveData: MutableLiveData<UserInfoEntity> by lazy { MutableLiveData<UserInfoEntity>() }
    val mGainMessageCodeLiveData: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val mLoadingLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val mPublicLiveData: MutableLiveData<Int> by lazy { MutableLiveData() }
    val mRefreshLiveData: MutableLiveData<RefreshStatusEntity> by lazy { MutableLiveData() }
    val mVersionLiveData: MutableLiveData<VersionEntity> by lazy { MutableLiveData() }
    fun loadUserInfo() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = UserInfoStore.get().getEntity()
            LogUtils.w(TAG, "$result")
            // mUserInfoLiveData.value = entity
            mUserInfoLiveData.value = result
        }
    }

    fun gainMessageCode(entity: RequestMessageCodeEntity) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = mRetrofitManger.create(BaseService::class.java)
                    .gainMessageCode(entity)
                disposeRetrofit(mGainMessageCodeLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun exitLoginLocal() {
        viewModelScope.launch {
            UserInfoStore.get().clear()
            ARoutersActivity.startLoginActivity()
            mPublicLiveData.value = STATUS_LOGIN_OUT
        }
    }


    fun <T> disposeRetrofit(
        liveData: MutableLiveData<T>?,
        response: Response<T>?,
        isLoadEmptyView: Boolean = false
    ) {
        mPublicLiveData.value = STATUE_REQUEST_END
        if (response == null) {
            mToastLiveData.value =
                DataCompat.getResString(com.sbnh.comm.R.string.default_http_error)
        } else {


            if (response.isSuccessful) {
                val message = response.message()
                LogUtils.w("disposeRetrofit", "$response---$message---")
                if (!DataCompat.isEmpty(message)) {
                    mToastLiveData.value = message
                }
                if (isLoadEmptyView) {
                    mPublicLiveData.value = STATUS_HIND_EMPTY_VIEW
                }
                val body = response.body()
                liveData?.value = body
                if (body is BasePagerEntity<*>) {
                    mPagerNum++
                    body.lastTimestamp?.let {
                        mLastTimestamp = it
                    }
                    body.lastTime?.let {
                        mLastTime = it
                    }

                    if (body.data is List<*>) {
                        mRefreshLiveData.value =
                            RefreshStatusEntity(CollectionCompat.getListSize(body.data))
                        /*if (body.data.isNotEmpty()) {
                            val any  = body.data.last()
                        }*/
                    }

                } else if (body is BaseEntity<*>) {
                    val data = body.data
                    if (data is BasePagerEntity2<*>) {
                        mPagerNum++
                        val pagerList = data.list
                        if (pagerList is List<*>) {
                            mRefreshLiveData.value =
                                RefreshStatusEntity(CollectionCompat.getListSize(pagerList))
                        }
                    }

                    //    if (body.list)
                }
                LogUtils.w("disposeRetrofit--", "成功")
            } else {
                mPublicLiveData.value = STATUE_HTTP_ERROR
                if (isLoadEmptyView) {
                    mPublicLiveData.value = STATUS_SHOW_EMPTY_VIEW
                }
                when (response.code()) {
                    IApiService.HttpCode.CLIENT_ERROR -> {
                        LogUtils.w("disposeRetrofit--", "客户端异常")
                    }
                    IApiService.HttpCode.CLIENT_NOT_LOGIN -> {
                        LogUtils.w("disposeRetrofit--", "用户未登录")
                        exitLoginLocal()
                    }
                    IApiService.HttpCode.SERVICE_ERROR -> {
                        LogUtils.w("disposeRetrofit--", "服务器异常")
                    }
                    else -> {

                    }
                }
                try {
                    val errorBody = response.errorBody()
                    if (errorBody != null) {
                        val errorMessage: String?
                        val errorJson = errorBody.string()
                        errorMessage = if (DataCompat.notEmpty(errorJson)) {
                            val gson = Gson()
                            val errorEntity = gson.fromJson(errorJson, ErrorResponse::class.java)
                            errorEntity?.message
                        } else if (response.code() == IApiService.HttpCode.NET_OFF_LINE) {
                            DataCompat.getResString(com.sbnh.comm.R.string.your_net_wait_off_line)
                        } else {
                            response.message()
                        }
                        LogUtils.w("errorJson--","$errorJson-----$errorMessage---${DataCompat.notEmpty(errorJson)}----${response.code()}")
                        mToastLiveData.value = errorMessage
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }


    }

    fun showLoading() {
        mLoadingLiveData.value = true
    }

    fun dismissLoading() {
        mLoadingLiveData.value = false
    }

    fun savePicture(pictureAny: Any) {
        val content = DataCompat.getContext()
        viewModelScope.launch(Dispatchers.IO) {
            val target = HealerMetaGlide.with(content)
                .asFile()
                .load(pictureAny)
                .submit()
            val glideFile = target.get()
            LogUtils.w("savePicture--", "${Thread.currentThread()}---1")
            CompressGlide.fromImage().create(content)
                .compressImage(glideFile.absolutePath, object : OnCompressGlideImageCallback {
                    override fun onResult(file: File) {
                        LogUtils.w("savePicture--", "${Thread.currentThread()}---2")
                        viewModelScope.launch(Dispatchers.IO) {
                            LogUtils.w("savePicture--", "${Thread.currentThread()}---3")
                            var os: OutputStream? = null
                            var fis: FileInputStream? = null
                            try {
                                val uri = content.contentResolver.insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    ContentValues()
                                )
                                os = content.contentResolver.openOutputStream(uri!!, "rw")
                                fis = FileInputStream(file.absolutePath)
                                val bytes = ByteArray(1024)
                                var read: Int
                                while (fis.read(bytes).also { read = it } != -1) {
                                    if (os != null) {
                                        os.write(bytes, 0, read)
                                        os.flush()
                                    }
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                fis?.close()
                                os?.close()
                            }
                            withContext(Dispatchers.Main) {
                                LogUtils.w("savePicture--", "${Thread.currentThread()}---4")
                                mToastLiveData.value =
                                    DataCompat.getResString(com.sbnh.comm.R.string.save_image_succeed)
                            }
                        }


                    }

                    override fun onError(errorMessage: String?) {
                        mToastLiveData.value =
                            DataCompat.getResString(com.sbnh.comm.R.string.save_image_fail)

                    }

                })

        }

    }

    fun loadAppVersion() {
        viewModelScope.launch {
            try {
                val result = mRetrofitManger.create(BaseService::class.java)
                    .loadAppVersion(DataCompat.getVersionCode())
                disposeRetrofit(mVersionLiveData, result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}