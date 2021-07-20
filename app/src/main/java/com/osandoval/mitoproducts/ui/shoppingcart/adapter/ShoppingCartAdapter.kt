package com.osandoval.mitoproducts.ui.shoppingcart.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osandoval.mitoproducts.core.BaseViewHolder
import com.osandoval.mitoproducts.data.model.ShoppingCartEntity
import com.osandoval.mitoproducts.databinding.FragmentShoppingCartItemBinding

class ShoppingCartAdapter(private var list: MutableList<ShoppingCartEntity>, val productClickListener: IOnProductClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>(){

    interface IOnProductClickListener{
        fun onItemClick(shoppingCart : ShoppingCartEntity)
        fun onDeleteClick(uid: Int, position: Int)
    }
    init {
        Log.d("meh", ": init se ejecutó ")
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewBinding = FragmentShoppingCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ShoppingCartViewHolder(viewBinding, parent.context)

        viewBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it!= DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener

            productClickListener.onItemClick(list[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
       when(holder){
           is ShoppingCartViewHolder -> holder.bind(list[position])
       }
    }

    override fun getItemCount(): Int = list.size

    fun removeAtPosition(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    private inner class ShoppingCartViewHolder(val binding: FragmentShoppingCartItemBinding, val context: Context)
        : BaseViewHolder<ShoppingCartEntity>(binding.root) {

        override fun bind(item: ShoppingCartEntity) {
            Glide.with(context).load(item.urlImage).centerCrop().into(binding.imageProduct)
            binding.textViewName.text = item.name
            binding.textViewDescription.text = item.description

            binding.buttonDeleteItem.setOnClickListener {
                productClickListener.onDeleteClick(item.uid, layoutPosition)
            }
        }
    }

}