package com.example.foodapp.data.repo

import com.example.foodapp.data.datasource.FoodDataSource
import com.example.foodapp.data.entity.CartFoodResponse
import com.example.foodapp.data.entity.FoodResponse
import retrofit2.Call

class FoodRepository(private val dataSource: FoodDataSource) {

     fun getAllFoods(): Call<FoodResponse> {
        return dataSource.getAllFoods()
    }

     fun addToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekAdet: Int,
        kullaniciAdi: String
    ): Call<CartFoodResponse> {
        return dataSource.addToCart(
            yemekAdi,
            yemekResimAdi,
            yemekFiyat,
            yemekAdet,
            kullaniciAdi
        )
    }

     fun getCartFoods(kullaniciAdi: String): Call<CartFoodResponse> {
        return dataSource.getCartFoods(kullaniciAdi)
    }

     fun removeFromCart(sepetYemekId: Int, kullaniciAdi: String): Call<CartFoodResponse> {
        return dataSource.removeFromCart(sepetYemekId, kullaniciAdi)
    }
}
