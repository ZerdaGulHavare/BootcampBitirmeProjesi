package com.example.foodapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.CartFoodResponse
import com.example.foodapp.data.entity.Food
import com.example.foodapp.data.entity.FoodResponse
import com.example.foodapp.data.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    val foodList = MutableLiveData<List<Food>>()
    val filteredList = MutableLiveData<List<Food>>()
    val errorMessage = MutableLiveData<String>()
    val addToCartSuccess = MutableLiveData<Boolean>()
    val addToCartError = MutableLiveData<String>()


    init {
        getAllFoods()
    }

    fun getAllFoods() {
        repository.getAllFoods().enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                if (response.isSuccessful) {
                    val fullList = response.body()?.yemekler ?: emptyList()
                    foodList.value = response.body()?.yemekler ?: emptyList()
                    filteredList.value = foodList.value
                } else {
                    errorMessage.value = "Yemekler alınamadı!"
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                errorMessage.value = "Hata: ${t.localizedMessage}"
            }
        })
    }

    fun addToCart(food: Food, adet: Int = 1, kullaniciAdi: String = "Zerda Gül Havare") {
        val fiyatInt = food.yemek_fiyat.toIntOrNull() ?: 0

        repository.addToCart(
            yemekAdi = food.yemek_adi,
            yemekResimAdi = food.yemek_resim_adi,
            yemekFiyat = fiyatInt,
            yemekAdet = adet,
            kullaniciAdi = kullaniciAdi
        ).enqueue(object : Callback<CartFoodResponse> {
            override fun onResponse(
                call: Call<CartFoodResponse>,
                response: Response<CartFoodResponse>
            ) {
                if (response.isSuccessful && response.body()?.success == 1) {
                    addToCartSuccess.value = true
                } else {
                    addToCartError.value = "Sepete eklenemedi!"
                }
            }

            override fun onFailure(call: Call<CartFoodResponse>, t: Throwable) {
                addToCartError.value = "Hata: ${t.localizedMessage}"
            }
        })
    }

    fun filterFoods(query: String) {
        val currentList = foodList.value ?: emptyList()
        if (query.isBlank()) {
            filteredList.value = currentList
        } else {
            filteredList.value = currentList.filter {
                it.yemek_adi.contains(query, ignoreCase = true)
            }
        }
    }
}
