package com.example.foodapp.retrofit

import com.example.foodapp.data.entity.CartFoodResponse
import com.example.foodapp.data.entity.FoodResponse
import retrofit2.Call
import retrofit2.http.*

interface UrunlerDao {

    // 🍽️ Tüm yemekleri getir
    @GET("yemekler/tumYemekleriGetir.php")
    fun getAllFoods(): Call<FoodResponse>

    // 🛒 Sepete yemek ekle
    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    fun addToCart(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<CartFoodResponse>

    // 🛍️ Sepetteki yemekleri getir
    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    fun getCartFoods(
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<CartFoodResponse>

    // ❌ Sepetten yemek sil
    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    fun removeFromCart(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<CartFoodResponse>
}
