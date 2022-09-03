package com.cdc.githuborganization.network

import com.cdc.githuborganization.network.interceptor.MockServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubAPIServiceFactory {

  companion object {
    private const val API_BASE_URL = "https://api.github.com"

    fun create(): GithubOrganizationAPIService {
      val logging = HttpLoggingInterceptor()
      logging.setLevel(HttpLoggingInterceptor.Level.BODY)
      val httpClient = OkHttpClient.Builder()
      httpClient.addInterceptor(logging)
//      httpClient.addInterceptor(MockServer())

      val retrofit = Retrofit.Builder()
        .client(httpClient.build())
        .baseUrl(API_BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .build()

      return retrofit.create(GithubOrganizationAPIService::class.java)
    }
  }
}
