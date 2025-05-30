package com.example.foodapp.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        fun getClient(baseUrl: String): Retrofit {
            // Logging interceptor oluştur
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)  // BODY: request ve response body dahil tüm loglar

            // OkHttpClient oluştur ve interceptor ekle
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            // Retrofit instance oluştur, custom client ile
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)  // Burada logging interceptor aktif oluyor
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
