package com.example.foodapp.data.entity

import java.io.Serializable

data class Food(
    val yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: String
) : Serializable

data class FoodResponse(
    val yemekler: List<Food>,
    val success: Int
) : Serializable

// Sepetteki yemekler için veri modeli (sepet_yemekler tablosu)
data class CartFood(
    val sepet_yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int,
    val yemek_siparis_adet: Int,
    val kullanici_adi: String
) : Serializable

// Sepetteki yemekler için API cevabı
data class CartFoodResponse(
    val sepet_yemekler: List<CartFood>,
    val success: Int
) : Serializable

// Sepete yemek eklemek için kullanılan model (isteğe bağlı, istek parametreleri)
data class AddToCartRequest(
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int,
    val yemek_siparis_adet: Int,
    val kullanici_adi: String
) : Serializable



//hoca bu şekilde yapmadı hocanın yaptığı şekil her model için farklı bir dosya
//oluşturmaktı fakat şu an ki modelimizde çok fazla veri olmadığından hepsini bir dosyada topladım