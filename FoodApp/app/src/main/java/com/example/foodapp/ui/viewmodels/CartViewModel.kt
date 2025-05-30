package com.example.foodapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.CartFood
import com.example.foodapp.data.entity.CartFoodResponse
import com.example.foodapp.data.repo.FoodRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(private val repository: FoodRepository) : ViewModel() {

    val cartList = MutableLiveData<List<CartFood>>()
    val errorMessage = MutableLiveData<String>()
    val kullaniciyim = "Zerda Gül Havare"

    fun getCartFoods(kullaniciAdi: String) {
        repository.getCartFoods(kullaniciyim).enqueue(object : Callback<CartFoodResponse> {
            override fun onResponse(call: Call<CartFoodResponse>, response: Response<CartFoodResponse>) {
                if (response.isSuccessful) {
                    cartList.value = response.body()?.sepet_yemekler ?: emptyList()
                } else {
                    errorMessage.value = "Sepet verileri alınamadı!"
                }
            }

            override fun onFailure(call: Call<CartFoodResponse>, t: Throwable) {
                errorMessage.value = "Hata: ${t.localizedMessage}"
            }
        })
    }

    fun removeFromCart(sepetYemekId: Int, kullaniciAdi: String) {
        repository.removeFromCart(sepetYemekId, kullaniciyim).enqueue(object : Callback<CartFoodResponse> {
            override fun onResponse(call: Call<CartFoodResponse>, response: Response<CartFoodResponse>) {
                getCartFoods(kullaniciyim)
            }

            override fun onFailure(call: Call<CartFoodResponse>, t: Throwable) {
                errorMessage.value = "Silme hatası: ${t.localizedMessage}"
            }
        })
    }
}
