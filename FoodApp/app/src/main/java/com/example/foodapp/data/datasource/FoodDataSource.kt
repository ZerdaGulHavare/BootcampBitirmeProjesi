package com.example.foodapp.data.datasource

import com.example.foodapp.data.entity.CartFoodResponse
import com.example.foodapp.data.entity.FoodResponse
import com.example.foodapp.retrofit.UrunlerDao
import retrofit2.Call

class FoodDataSource(private val foodsDao: UrunlerDao) {

    fun getAllFoods(): Call<FoodResponse> {
        return foodsDao.getAllFoods()
    }

    fun addToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekAdet: Int,
        kullaniciAdi: String
    ): Call<CartFoodResponse> {
        return foodsDao.addToCart(yemekAdi, yemekResimAdi, yemekFiyat, yemekAdet, kullaniciAdi)
    }

    fun getCartFoods(kullaniciAdi: String): Call<CartFoodResponse> {
        return foodsDao.getCartFoods(kullaniciAdi)
    }

    fun removeFromCart(sepetYemekId: Int, kullaniciAdi: String): Call<CartFoodResponse> {
        return foodsDao.removeFromCart(sepetYemekId, kullaniciAdi)
    }
}
