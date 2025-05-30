package com.example.foodapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.Food
import com.example.foodapp.databinding.FragmentDetailBinding
import com.example.foodapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedFood: Food
    private val username = "Zerda Gül Havare" // Giriş yapan kullanıcı ile değiştir

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        arguments?.let {
            selectedFood = it.getSerializable("selected_food") as Food
        }

        setupUI()

        binding.btnAddToCart.setOnClickListener {
            val quantity = binding.etQuantity.text.toString().toIntOrNull() ?: 1
            detailViewModel.addToCart(
                yemekAdi = selectedFood.yemek_adi,
                yemekResimAdi = selectedFood.yemek_resim_adi,
                yemekFiyat = selectedFood.yemek_fiyat.toInt(),
                yemekAdet = quantity,
                kullaniciAdi = username
            )
        }

        observeViewModel()

        return binding.root
    }

    private fun setupUI() {
        binding.tvFoodName.text = selectedFood.yemek_adi
        binding.tvFoodDescription.text = "Fiyat: ${selectedFood.yemek_fiyat} ₺"
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${selectedFood.yemek_resim_adi}"
        Glide.with(requireContext()).load(imageUrl).into(binding.ivFoodImage)
    }

    private fun observeViewModel() {
        detailViewModel.successMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        detailViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

