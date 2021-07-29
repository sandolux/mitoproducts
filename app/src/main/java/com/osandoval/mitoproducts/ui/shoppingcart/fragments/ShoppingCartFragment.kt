package com.osandoval.mitoproducts.ui.shoppingcart.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.shoppingCart.LocalShoppingCartDataSource
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity
import com.osandoval.mitoproducts.databinding.FragmentShoppingCartBinding
import com.osandoval.mitoproducts.domain.shoppingcart.ShoppingCartRepository
import com.osandoval.mitoproducts.ui.shoppingcart.adapter.ShoppingCartAdapter
import com.osandoval.mitoproducts.ui.shoppingcart.viewmodel.ShoppingCartViewModel
import com.osandoval.mitoproducts.ui.shoppingcart.viewmodel.ShoppingCartViewModelFactory
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences

class ShoppingCartFragment : Fragment(R.layout.fragment_shopping_cart), ShoppingCartAdapter.IOnProductClickListener {
    private val TAG = "APP_MITOPRODUCT"
    private val ORIGIN = "[SHOPPING_CART_FRAGMENT]"

    private lateinit var binding : FragmentShoppingCartBinding
    private lateinit var adapter : ShoppingCartAdapter

    private val viewModel by viewModels<ShoppingCartViewModel>{
        ShoppingCartViewModelFactory(
            ShoppingCartRepository(
                LocalShoppingCartDataSource(AppDatabase.getDatabase(requireContext()).shoppingCartDao())
            ),
            SharedPreferences(requireContext())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentShoppingCartBinding.bind(view)

        setGetShoppingCartObserver()
        setButtonCreateOrderListener()

    }

    private fun setGetShoppingCartObserver() {
        viewModel.getShoppingCart().observe(viewLifecycleOwner, {result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    adapter = ShoppingCartAdapter(result.data, this@ShoppingCartFragment)
                    binding.recyclerViewShoppingCart.addItemDecoration(DividerItemDecoration(context,1))
                    binding.recyclerViewShoppingCart.adapter = adapter
                }
                is Resource.Failure -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: ${result.exception}")
                }
            }
        })
    }

    private fun setButtonCreateOrderListener() {
        binding.buttonCreateOrder.setOnClickListener {
            viewModel.insertOrders().observe(viewLifecycleOwner, {result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d(TAG, "$ORIGIN onViewCreated: LOADING...")
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "$ORIGIN setButtonCreateOrderListener: El pedido fue creado ${result.data} ")
                        findNavController().navigate(ShoppingCartFragmentDirections.actionNavShoppingCartToNavOrders())
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "$ORIGIN onViewCreated: ${result.exception}")
                    }
                }
            })
        }
    }

    override fun onItemClick(shoppingCart: ShoppingCartEntity) {
        //TODO: IMPLEMENTAR MODAL CON INFORMACIÓN DEL ITEM SELECCIONADO
        Log.d(TAG, "$ORIGIN onItemClick: $shoppingCart")
    }

    override fun onDeleteClick(uid: Int, position : Int) {
        viewModel.deleteItem(uid).observe(viewLifecycleOwner, { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "$ORIGIN onDeleteClick: LOADING...")
                }
                is Resource.Success -> {
                    Log.d(TAG, "$ORIGIN onDeleteClick: ELIMINADO $uid")
                    adapter.removeAtPosition(position)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "$ORIGIN onDeleteClick: ${result.exception}")
                }
            }
        })
    }
}