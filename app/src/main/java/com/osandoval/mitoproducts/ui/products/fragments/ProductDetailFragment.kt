package com.osandoval.mitoproducts.ui.products.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.product.LocalProductDataSource
import com.osandoval.mitoproducts.data.remote.product.RemoteProductDataSource
import com.osandoval.mitoproducts.data.remote.RetrofitClient
import com.osandoval.mitoproducts.databinding.FragmentProductDetailBinding
import com.osandoval.mitoproducts.domain.product.ProductRepository
import com.osandoval.mitoproducts.ui.products.viewmodel.ProductDetailViewModel
import com.osandoval.mitoproducts.ui.products.viewmodel.ProductDetailViewModelFactory
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {
    private val TAG = "APP_MITOPRODUCT"
    private val ORIGIN = "[PRODUCT_DETAIL_FRAGMENT]"

    private val args by navArgs<ProductDetailFragmentArgs>()
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel by viewModels<ProductDetailViewModel> {
        ProductDetailViewModelFactory(
            ProductRepository(
                RemoteProductDataSource(RetrofitClient.webService),
                LocalProductDataSource(AppDatabase.getDatabase(requireContext()).productDao())
            ),
            SharedPreferences(requireContext())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductDetailBinding.bind(view)

        viewModel.getProduct(args.id).observe(viewLifecycleOwner, {result->
            when(result){
                is Resource.Loading -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: LOADING")
                }
                is Resource.Success -> {
                    Glide.with(this@ProductDetailFragment,).load(result.data.urlImage).centerCrop().into(binding.imageProduct)
                    binding.textViewPrice.text = "$${result.data.price}"
                    binding.textViewName.text = result.data.name
                    binding.textViewDescription.text = result.data.description
                }
                is Resource.Failure -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: ${result.exception}")
                }
            }
        })

        setAddShoppingCartButtonListener()
    }

    private fun setAddShoppingCartButtonListener() {
        binding.buttonAddShoppingCart.setOnClickListener {
            viewModel.addShoppingCart(args.id).observe(viewLifecycleOwner, {result->
                when(result){
                    is Resource.Loading -> {
                        Log.d(TAG, "$ORIGIN  onViewCreated: LOADING")
                    }
                    is Resource.Success -> {
                        Toast.makeText(context,"Se agreg?? al carro", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "$ORIGIN  onViewCreated: ${result.exception}")
                    }
                }
            })
        }
    }
}