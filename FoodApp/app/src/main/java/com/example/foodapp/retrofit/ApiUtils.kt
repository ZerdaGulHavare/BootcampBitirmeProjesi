package com.example.foodapp.retrofit

class ApiUtils {
    companion object {
        private const val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getUrunlerDao(): UrunlerDao {
            return RetrofitClient.getClient(BASE_URL).create(UrunlerDao::class.java)
        }
    }
}
