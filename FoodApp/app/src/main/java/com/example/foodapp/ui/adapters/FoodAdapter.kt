package com.example.foodapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemFoodBinding
import com.example.foodapp.data.entity.Food

class FoodAdapter(
    private var foodList: List<Food>,
    private val onItemClick: (Food) -> Unit,
    private val onAddToCartClick: (Food, Int) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val quantities = mutableMapOf<Int, Int>()

    inner class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.tvFoodName.text = food.yemek_adi
            binding.tvFoodPrice.text = "₺${food.yemek_fiyat}"

            Glide.with(binding.ivFoodImage.context)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}")
                .into(binding.ivFoodImage)

            val currentQuantity = quantities[food.yemek_id.toInt()] ?: 1
            binding.tvQuantity.text = currentQuantity.toString()

            binding.btnIncrease.setOnClickListener {
                val newQty = (quantities[food.yemek_id.toInt()] ?: 1) + 1
                quantities[food.yemek_id.toInt()] = newQty
                binding.tvQuantity.text = newQty.toString()
            }

            binding.btnDecrease.setOnClickListener {
                val newQty = ((quantities[food.yemek_id.toInt()] ?: 1) - 1).coerceAtLeast(1)
                quantities[food.yemek_id.toInt()] = newQty
                binding.tvQuantity.text = newQty.toString()
            }

            binding.btnAddToCart.setOnClickListener {
                val adet = quantities[food.yemek_id.toInt()] ?: 1
                onAddToCartClick(food, adet)
            }

            binding.root.setOnClickListener {
                onItemClick(food)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    fun updateData(newList: List<Food>) {
        foodList = newList
        notifyDataSetChanged()
        Log.d("DEBUG_FoodAdapter", "updateData çağrıldı, liste boyutu: ${foodList.size}")
    }
}
