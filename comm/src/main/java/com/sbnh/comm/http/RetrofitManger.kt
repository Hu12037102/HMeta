package com.sbnh.comm.http

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.sbnh.comm.BuildConfig
import com.sbnh.comm.compat.CollectionCompat
import com.sbnh.comm.compat.DataCompat
import com.sbnh.comm.compat.NetWorkCompat
import com.sbnh.comm.config.AppConfig
import com.sbnh.comm.digest.MD5Compat
import com.sbnh.comm.digest.RSACompat
import com.sbnh.comm.digest.SHA1Compat
import com.sbnh.comm.entity.request.RequestEncryptEntity
import com.sbnh.comm.factory.FileFactory
import com.sbnh.comm.factory.FileFactory.TYPE_HTTP
import com.sbnh.comm.info.UserInfoStore
import com.sbnh.comm.utils.LogUtils
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

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
    private val mUrlInterceptor: Interceptor by lazy {
        Interceptor { chain ->
            var request = chain.request()
            var url: HttpUrl = request.url
            val pathList = url.encodedPathSegments
            if (CollectionCompat.notEmptyList(pathList)) {
                val path = pathList[0]
                if (AppConfig.isDebug() && TextUtils.equals(
                        path,
                        IApiService.EncodedPath.RELEASE_PAYMENT_START
                    )
                ) {
                    url = url.newBuilder()
                        .setEncodedPathSegment(0, IApiService.EncodedPath.DEBUG_PAYMENT_START)
                        .build()
                }

            }
            request = request.newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }
    }

    companion object {
        val Instance: RetrofitManger by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RetrofitManger() }
        private const val DEFAULT_TIME_OUT_MILLISECONDS = 15 * 1000L
        private const val CACHE_MEX_LENGTH = 200 * 1024 * 1024L
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
            var request: Request = chain.request()
            try {
                var signature = ""
                var nonce = ""
                val requestBody = request.body
                val privateKey = runBlocking { UserInfoStore.get().getPrivateKey() }
                if (requestBody != null && DataCompat.notEmpty(privateKey)) {
                    val entity = RequestEncryptEntity()
                    val gson = Gson()
                    val json = gson.toJson(entity)
                    nonce = RSACompat.encryptPrivateKey(privateKey, json)
                    val buffer = Buffer()
                    requestBody.writeTo(buffer)
                    signature = buffer.readString(StandardCharsets.UTF_8)
                    signature = "${signature}_${entity.uuid}"
                    signature = MD5Compat.stringToString(signature)
                    signature = RSACompat.encryptPrivateKey(privateKey, signature)
                }

                val uuid = UUID.randomUUID().toString()
                val timestamp = System.currentTimeMillis()
                val appId = AppConfig.getHealerMetaAppId()
                val appKey = AppConfig.getHealerMetaAppKey()
                val all = "$appId$appKey$timestamp$uuid"
                val encryptText = SHA1Compat.hamcsha1(
                    all.toByteArray(Charset.defaultCharset()),
                    appKey.toByteArray(Charset.defaultCharset())
                )
                val sid = runBlocking { UserInfoStore.get().getEntity()?.sid }
                request = request.newBuilder()
                    .addHeader(IApiService.Key.CONTENT_TYPE, "application/json")
                    .addHeader(IApiService.Key.CHARSET, Charsets.UTF_8.name())
                    .addHeader(IApiService.Key.ACCESS_TOKEN, encryptText)
                    .addHeader(IApiService.Key.TS, "$timestamp")
                    .addHeader(IApiService.Key.UUID, uuid)
                    .addHeader(IApiService.Key.SID, sid ?: "")
                    .addHeader(IApiService.Key.VERSION, "${DataCompat.getVersionCode()}")
                    .addHeader(IApiService.Key.OS, IApiService.Value.ANDROID)
                    .addHeader(IApiService.Key.NONCE, nonce)
                    .addHeader(IApiService.Key.SIGNATURE, signature)
                    .build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@Interceptor chain.proceed(request)

        }


    }

    private fun initCacheInterceptor() {
        mCacheInterceptor = Interceptor { chain ->
            LogUtils.w("initCacheInterceptor--", "${NetWorkCompat.isNetComment()}")
            var request = chain.request()
            request = if (NetWorkCompat.isNetComment()) {
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            var response = chain.proceed(request)
            response = if (NetWorkCompat.isNetComment()) {
                response.newBuilder().removeHeader("Pragma")
                    .header("Cache-Control", "public,max-age=" + 60 * 60).build();
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
        val cacheDir = FileFactory.createCacheDir(TYPE_HTTP)
        val builder = OkHttpClient.Builder()
            .cache(if (cacheDir == null) null else Cache(cacheDir, CACHE_MEX_LENGTH))
            .retryOnConnectionFailure(true)
            .callTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .connectTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_TIME_OUT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .addInterceptor(mHeadInterceptor)
            .addInterceptor(mCacheInterceptor)
        if (AppConfig.isDebug()) {
            builder.addInterceptor(mUrlInterceptor)
        }
        builder.addInterceptor(mLoggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        mOkHttpClient = builder.build()

    }

    private fun initRetrofit() {
        mRetrofit = Retrofit.Builder()
            .client(mOkHttpClient)
            .baseUrl(AppConfig.getBaseUrl())
            // .baseUrl("https://www.baidu.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    fun <T> create(clazz: Class<T>): T = mRetrofit.create(clazz)

}