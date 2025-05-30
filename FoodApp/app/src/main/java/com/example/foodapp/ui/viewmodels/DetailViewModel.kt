package com.example.foodapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.CartFoodResponse
import com.example.foodapp.data.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun addToCart(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekAdet: Int,
        kullaniciAdi: String
    ) {
        repository.addToCart(yemekAdi, yemekResimAdi, yemekFiyat, yemekAdet, kullaniciAdi)
            .enqueue(object : Callback<CartFoodResponse> {
                override fun onResponse(
                    call: Call<CartFoodResponse>,
                    response: Response<CartFoodResponse>
                ) {
                    if (response.isSuccessful) {
                        successMessage.value = "Sepete eklendi ✅"
                    } else {
                        errorMessage.value = "Ekleme başarısız"
                    }
                }

                override fun onFailure(call: Call<CartFoodResponse>, t: Throwable) {
                    errorMessage.value = "Hata: ${t.localizedMessage}"
                }
            })
    }
}
