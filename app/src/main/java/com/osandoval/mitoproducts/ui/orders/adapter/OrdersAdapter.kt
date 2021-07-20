package com.osandoval.mitoproducts.ui.orders.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.osandoval.mitoproducts.core.BaseViewHolder
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.databinding.FragmentOrdersItemBinding

class OrdersAdapter(private val list: List<OrderEntity>,  private val itemClickListener: IOnItemClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface IOnItemClickListener{
        fun onItemClick(orderEntity: OrderEntity)
        fun onNextButtonClick(uid:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewBinding = FragmentOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = OrderViewHolder(viewBinding, parent.context)

        viewBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it!=DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onItemClick(list[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is OrderViewHolder-> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    private inner class OrderViewHolder(val binding: FragmentOrdersItemBinding, val context: Context)
        : BaseViewHolder<OrderEntity>(binding.root) {

        override fun bind(item: OrderEntity) {
            binding.textViewUID.text = item.uid

            binding.buttonNext.setOnClickListener {
                itemClickListener.onNextButtonClick(item.uid)
            }
        }

    }
}