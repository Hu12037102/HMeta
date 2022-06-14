package com.sbnh.comm.http

import com.sbnh.comm.compat.NetWorkCompat
import com.sbnh.comm.utils.LogUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/6/10 11:43
 * 更新时间: 2022/6/10 11:43
 * 描述:网络请求框架
 */
class RetrofitManger private constructor() {
    private lateinit var mLoggerInterceptor: HttpLoggingInterceptor
    private lateinit var mHeadInterceptor: Interceptor
    private lateinit var mOkHttpClient: OkHttpClient
    private lateinit var mRetrofit: Retrofit
    private lateinit var mCacheInterceptor: Interceptor

    companion object {
        val Instance: RetrofitManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManger() }
        private const val DEFAULT_TIME_OUT_MILLISECONDS = 15 * 1000L
    }

    init {
        initInterceptor()
        initOKHttp()
        initRetrofit()
    }

    private fun initInterceptor() {
        initLoggerInterceptor()
        initHeadInterceptor()
        initCacheInterceptor()
    }


    private fun initLoggerInterceptor() {
        mLoggerInterceptor = HttpLoggingInterceptor {
            LogUtils.w("HttpLoggingInterceptor", it + "")
        }
    }

    private fun initHeadInterceptor() {
        mHeadInterceptor = Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", Charsets.UTF_8.name())
                .build()
            return@Interceptor chain.proceed(request)
        }


    }

    private fun initCacheInterceptor() {
        mCacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            request = if (NetWorkCompat.isNetComment()) {
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            var response = chain.proceed(request)
            response = if (NetWorkCompat.isNetComment()) {
                response.newBuilder().removeHeader("Pragma")
                    .header("Cache-Control", "public,max-age=" + 60 * 60)
                    .build()
            } else {
                response.newBuilder().removeHeader("Pragma")
                    .header(
                        "Cache-Control",
                        "public,only-if-cached,max-stale=" + 7 * 24 * 60 * 60
                    ).build()
            }

            response
        }
    }

    private fun initOKHttp() {
        mOkHttpClient = OkHttpClient()
            .newBuilder()
            .callTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .connectTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .addInterceptor(mHeadInterceptor)
            .addInterceptor(mLoggerInterceptor)
            .addInterceptor(mCacheInterceptor)
            .build()

    }

    private fun initRetrofit() {
        mRetrofit = Retrofit.Builder()
            .baseUrl(IApiBaseService.BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }


}