package com.example.foodapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.CartFood
import com.example.foodapp.databinding.ItemCardBinding

class CartAdapter(
    private var cartList: List<CartFood>,
    private val onRemoveClick: (CartFood) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartFood: CartFood) {
            binding.tvCartFoodName.text = cartFood.yemek_adi
            binding.tvCartFoodQuantity.text = "Adet: ${cartFood.yemek_siparis_adet}"

            val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${cartFood.yemek_resim_adi}"
            Glide.with(binding.ivCartFoodImage.context)
                .load(imageUrl)
                .into(binding.ivCartFoodImage)

            binding.btnRemoveFromCart.setOnClickListener {
                onRemoveClick(cartFood)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int = cartList.size

    fun updateCart(newList: List<CartFood>) {
        cartList = newList
        notifyDataSetChanged()
    }
}
