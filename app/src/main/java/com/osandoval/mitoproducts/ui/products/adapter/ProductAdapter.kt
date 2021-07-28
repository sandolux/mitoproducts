package com.osandoval.mitoproducts.ui.products.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osandoval.mitoproducts.core.BaseViewHolder
import com.osandoval.mitoproducts.data.model.ProductEntity
import com.osandoval.mitoproducts.databinding.FragmentProductsItemBinding

class ProductAdapter(private val list: List<ProductEntity>, private val itemClickListener: IOnItemClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface IOnItemClickListener{
        fun onItemClick(product: ProductEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
      val viewBinding = FragmentProductsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      val holder = ProductViewHolder(viewBinding, parent.context)

        viewBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it!= DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onItemClick(list[position])
        }

      return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ProductViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    private inner class ProductViewHolder(val binding: FragmentProductsItemBinding, val context: Context)
        : BaseViewHolder<ProductEntity>(binding.root) {

        override fun bind(item: ProductEntity) {
           Glide.with(context).load(item.urlImage).centerCrop().into(binding.imageProduct)
           binding.textViewName.text = item.name
        }
    }

}