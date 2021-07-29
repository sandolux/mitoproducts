package com.osandoval.mitoproducts.ui.orders.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.order.LocalOrderDataSource
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.databinding.FragmentOrdersBinding
import com.osandoval.mitoproducts.domain.order.OrderRepository
import com.osandoval.mitoproducts.ui.orders.adapter.OrdersAdapter
import com.osandoval.mitoproducts.ui.orders.viewmodel.OrderViewModel
import com.osandoval.mitoproducts.ui.orders.viewmodel.OrderViewModelFactory
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences

class OrdersFragment : Fragment(R.layout.fragment_orders), OrdersAdapter.IOnItemClickListener {
    private val TAG = "APP_MITOPRODUCT"
    private val ORIGIN = "[ORDERS_FRAGMENT]"
    private lateinit var binding : FragmentOrdersBinding
    private val viewModel by viewModels<OrderViewModel> {
        OrderViewModelFactory(
            OrderRepository(
                LocalOrderDataSource(AppDatabase.getDatabase(requireContext()).orderDao())
            ),
            SharedPreferences(requireContext())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersBinding.bind(view)
        setHasOptionsMenu(true)

        viewModel.getOrders().observe(viewLifecycleOwner, { result->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    binding.recyclerViewOrders.addItemDecoration(DividerItemDecoration(context,1))
                    binding.recyclerViewOrders.adapter = OrdersAdapter(result.data, this@OrdersFragment)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: ${result.exception}")
                }
            }
        })
    }

    override fun onItemClick(orderEntity: OrderEntity) {
        //TODO: IMPLEMENTAR MODAL CON INFORMACIÓN DEL PEDIDO
        Log.d(TAG, "$ORIGIN onItemClick: $orderEntity")
    }

    override fun onNextButtonClick(uid: String) {
        val action = OrdersFragmentDirections.actionNavOrdersToOrderDetailFragment(uid)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return(when (item.itemId) {
            R.id.action_shopping_cart -> {
                findNavController().navigate(OrdersFragmentDirections.actionNavOrdersToNavShoppingCart())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        })

    }
}