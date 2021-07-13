package com.osandoval.mitoproducts.ui.products.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.LocalProductDataSource
import com.osandoval.mitoproducts.data.remote.RemoteProductDataSource
import com.osandoval.mitoproducts.data.remote.RetrofitClient
import com.osandoval.mitoproducts.databinding.FragmentProductsBinding
import com.osandoval.mitoproducts.domain.product.ProductRepository
import com.osandoval.mitoproducts.ui.products.viewmodel.ProductViewModelFactory
import com.osandoval.mitoproducts.ui.products.viewmodel.ProductsViewModel
import java.util.*
import androidx.lifecycle.Observer


class ProductsFragment : Fragment(R.layout.fragment_products) {
    private val TAG ="Meh"
    private lateinit var binding: FragmentProductsBinding
    private val viewModel by viewModels<ProductsViewModel> {
        ProductViewModelFactory(
            ProductRepository(
                RemoteProductDataSource(RetrofitClient.webService),
                LocalProductDataSource(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductsBinding.bind(view)

        viewModel.getProducts().observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated: ${it.data}")
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onViewCreated: ${it.exception}")
                }
            }
        })
    }
}