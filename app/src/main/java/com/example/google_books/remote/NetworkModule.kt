package com.example.google_books.remote

import android.content.Context
import com.example.google_books.BuildConfig
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val baseUrl = "https://www.googleapis.com"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private val gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Singleton
    @Provides
    fun provideClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024
        val cacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        val cache = Cache(cacheDirectory, cacheSize.toLong())

        val queue = SynchronousQueue<Runnable>()
        val executor = ThreadPoolExecutor(5, 30, 15, TimeUnit.SECONDS, queue)
        val dispatcher = Dispatcher(executor)

        val builder = OkHttpClient().newBuilder()
            .cache(cache)
            .dispatcher(dispatcher)
            .https(baseUrl)
            .addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()


    @Singleton
    @Provides
    fun provideAppService(retrofit: Retrofit): AppService =
        retrofit.create(AppService::class.java)
}

private fun OkHttpClient.Builder.https(baseUrl: String): OkHttpClient.Builder {
    if (baseUrl.isNotEmpty() && baseUrl.contains("https")) {
        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, null, null)

            this.sslSocketFactory(sslContext.socketFactory)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return this
}