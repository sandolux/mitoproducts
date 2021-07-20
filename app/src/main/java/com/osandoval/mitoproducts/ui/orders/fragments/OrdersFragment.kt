package com.osandoval.mitoproducts.ui.orders.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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
import com.osandoval.mitoproducts.ui.products.adapter.ProductAdapter

class OrdersFragment : Fragment(R.layout.fragment_orders), OrdersAdapter.IOnItemClickListener {
    private val TAG ="Meh"
    private lateinit var binding : FragmentOrdersBinding
    private val viewModel by viewModels<OrderViewModel> {
        OrderViewModelFactory(
            OrderRepository(
                LocalOrderDataSource(AppDatabase.getDatabase(requireContext()).orderDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrdersBinding.bind(view)

        viewModel.getOrders().observe(viewLifecycleOwner, { result->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    binding.recyclerViewOrders.addItemDecoration(DividerItemDecoration(context,1))
                    binding.recyclerViewOrders.adapter = OrdersAdapter(result.data, this@OrdersFragment)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "onViewCreated: ${result.exception}")
                }
            }
        })
    }

    override fun onItemClick(orderEntity: OrderEntity) {
        Log.d(TAG, "onItemClick: $orderEntity")
    }

    override fun onNextButtonClick(uid: String) {
        Log.d(TAG, "onNextButtonClick: $uid")
                                             // action_nav_orders_to_orderDetailFragment
        val action = OrdersFragmentDirections.actionNavOrdersToOrderDetailFragment(uid)
        findNavController().navigate(action)
    }
}