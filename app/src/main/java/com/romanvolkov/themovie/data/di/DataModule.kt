package com.romanvolkov.themovie.data.di

import com.romanvolkov.themovie.BuildConfig
import com.romanvolkov.themovie.core.NoInternetException
import com.romanvolkov.themovie.core.common.Constants.API_KEY
import com.romanvolkov.themovie.core.common.Constants.BASE_URL
import com.romanvolkov.themovie.core.common.Constants.DEFAULT_LANGUAGE
import com.romanvolkov.themovie.data.api.MovieApi
import com.romanvolkov.themovie.data.repository.MovieRepositoryImpl
import com.romanvolkov.themovie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

@Module
class DataModule {

    @Provides
    fun provideRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    fun provideMovieApi(okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(MovieApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging by lazy {
            HttpLoggingInterceptor { Timber.tag("OkHttp").d(it) }.apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.BASIC
                }
            }
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(logging)
            addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .url(
                        original.url.newBuilder()
                            .addQueryParameter("language", DEFAULT_LANGUAGE)
                            .addQueryParameter("api_key", API_KEY)
                            .build()
                    )
                    .method(original.method, original.body)
                try {
                    chain.proceed(request.build())
                } catch (e: SocketTimeoutException) {
                    throw NoInternetException(message = original.url.toString())
                } catch (e: UnknownHostException) {
                    throw NoInternetException(message = original.url.toString())
                } catch (e: ConnectException) {
                    throw NoInternetException(message = original.url.toString())
                } catch (e: SSLHandshakeException) {
                    throw NoInternetException(message = original.url.toString())
                } catch (e: IOException) {
                    throw NoInternetException(message = original.url.toString())
                }
            }
        }.build()
    }
}