package com.osandoval.mitoproducts.ui.orders.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.order.LocalOrderDataSource
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.databinding.FragmentOrderDetailBinding
import com.osandoval.mitoproducts.domain.order.OrderRepository
import com.osandoval.mitoproducts.ui.orders.viewmodel.OrderDetailViewModel
import com.osandoval.mitoproducts.ui.orders.viewmodel.OrderDetailViewModelFactory
import com.osandoval.mitoproducts.ui.products.adapter.ProductAdapter

class OrderDetailFragment : Fragment(R.layout.fragment_order_detail), ProductAdapter.IOnItemClickListener {
    private val TAG = "APP_MITOPRODUCT"
    private val ORIGIN = "[ORDER_DETAIL_FRAGMENT]"
    private val args by navArgs<OrderDetailFragmentArgs>()
    private lateinit var binding: FragmentOrderDetailBinding

    private val viewModel by viewModels<OrderDetailViewModel> {
        OrderDetailViewModelFactory(
            OrderRepository(
                LocalOrderDataSource(AppDatabase.getDatabase(requireContext()).orderDao())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOrderDetailBinding.bind(view)

        viewModel.getOrdersDetail(args.uid).observe(viewLifecycleOwner, { result->
            when(result) {
                is Resource.Loading -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: LOADING...")
                }
                is Resource.Success -> {
                    binding.recyclerViewProducts.layoutManager = GridLayoutManager(context,2)
                    binding.recyclerViewProducts.adapter = ProductAdapter(result.data, this@OrderDetailFragment)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "$ORIGIN onViewCreated: ${result.exception}")
                }
            }
        })
    }

    override fun onItemClick(product: ProductEntity) {
        //TODO(): IMPLEMENTAR MODAL CON INFORMACIÓN DEL PRODUCTO
        Log.d(TAG, "$ORIGIN onItemClick: $product")
    }
}