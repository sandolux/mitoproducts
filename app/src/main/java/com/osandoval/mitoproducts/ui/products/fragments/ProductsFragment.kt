package com.osandoval.mitoproducts.ui.products.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.product.LocalProductDataSource
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.data.remote.product.RemoteProductDataSource
import com.osandoval.mitoproducts.data.remote.RetrofitClient
import com.osandoval.mitoproducts.databinding.FragmentProductsBinding
import com.osandoval.mitoproducts.domain.product.ProductRepository
import com.osandoval.mitoproducts.ui.products.adapter.ProductAdapter
import com.osandoval.mitoproducts.ui.products.viewmodel.ProductViewModelFactory
import com.osandoval.mitoproducts.ui.products.viewmodel.ProductsViewModel


class ProductsFragment : Fragment(R.layout.fragment_products), ProductAdapter.IOnItemClickListener {
    private val TAG = "APP_MITOPRODUCT"
    private val ORIGIN = "[PRODUCTS_FRAGMENT]"

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
        setHasOptionsMenu(true)

        viewModel.getProducts().observe(viewLifecycleOwner, { result->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    binding.recyclerViewProducts.layoutManager = GridLayoutManager(context,2)
                    binding.recyclerViewProducts.adapter = ProductAdapter(result.data, this@ProductsFragment)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "$ORIGIN onViewCreated ProductsFragment: ${result.exception}")
                }
            }
        })
    }

    override fun onItemClick(product: ProductEntity) {
       val action = ProductsFragmentDirections.actionNavProductsToProductDetailFragment(product.id)
       findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return(when (item.itemId) {
            R.id.action_shopping_cart -> {
                findNavController().navigate(ProductsFragmentDirections.actionNavProductsToNavShoppingCart())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        })

    }
}