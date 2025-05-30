package com.example.foodapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.ui.adapters.FoodAdapter
import com.example.foodapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var foodAdapter: FoodAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        observeViewModel()

        viewModel.getAllFoods()  // Veriyi çekmeye zorla
    }

    private fun setupRecyclerView() {
        foodAdapter = FoodAdapter(
            emptyList(),
            onItemClick = { food ->
                val bundle = Bundle().apply {
                    putSerializable("selected_food", food)
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            },
            onAddToCartClick = { food, adet ->
                viewModel.addToCart(food, adet)
                Toast.makeText(requireContext(), "${food.yemek_adi} x$adet sepete eklendi", Toast.LENGTH_SHORT).show()
            }
        )

        binding.foodRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = foodAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterFoods(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.filteredList.observe(viewLifecycleOwner) { list ->
            foodAdapter.updateData(list)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), "Hata: $msg", Toast.LENGTH_LONG).show()
        }

        viewModel.addToCartError.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), "Sepete ekleme hatası: $msg", Toast.LENGTH_LONG).show()
        }

        viewModel.addToCartSuccess.observe(viewLifecycleOwner) { success ->
            if (success) Toast.makeText(requireContext(), "Sepete eklendi!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
