package com.example.foodapp.ui.fragments

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.data.datasource.FoodDataSource
import com.example.foodapp.data.repo.FoodRepository
import com.example.foodapp.databinding.FragmentCartBinding
import com.example.foodapp.retrofit.ApiUtils
import com.example.foodapp.ui.adapters.CartAdapter
import com.example.foodapp.viewmodel.CartViewModel
import com.example.foodapp.viewmodel.CartViewModelFactory
import com.airbnb.lottie.LottieAnimationView // ✅ Lottie için gerekli import

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter
    private lateinit var cartViewModel: CartViewModel

    private val username = "0l0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        val dataSource = FoodDataSource(ApiUtils.getUrunlerDao())
        val repository = FoodRepository(dataSource)
        val factory = CartViewModelFactory(repository)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        cartViewModel.getCartFoods(username)

        binding.btnOrder.setOnClickListener {
            animateMotor()
        }

        return binding.root
    }

    private fun animateMotor() {
        val motor = binding.lottieMotor
        motor.visibility = View.VISIBLE
        motor.playAnimation()

        motor.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                motor.visibility = View.GONE
                Toast.makeText(requireContext(), "Siparişiniz alındı! 🏍️", Toast.LENGTH_SHORT).show()
                motor.removeAnimatorListener(this)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(emptyList()) { cartFood ->
            cartViewModel.removeFromCart(cartFood.sepet_yemek_id.toInt(), username)
        }

        binding.cardRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = cartAdapter
        }
    }

    private fun observeViewModel() {
        cartViewModel.cartList.observe(viewLifecycleOwner) { cartList ->
            cartAdapter.updateCart(cartList)
        }

        cartViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
