package com.osandoval.mitoproducts.ui.shoppingcart.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.model.OrderDetailEntity
import com.osandoval.mitoproducts.data.model.OrderEntity
import com.osandoval.mitoproducts.data.model.toOrderDetailList
import com.osandoval.mitoproducts.domain.shoppingcart.ShoppingCartRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.*

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) : ViewModel() {
    fun getShoppingCart() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.getShoppingCart()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteItem(uid: Int) = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.deleteItem(uid)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun wipeShoppingCart() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.wipeShoppingCart()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun insertOrders() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            val orderUid= UUID.randomUUID().toString()
            repository.insertOrders( repository.getShoppingCart().toOrderDetailList(orderUid), orderUid )
            repository.wipeShoppingCart()
            emit(Resource.Success(true))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class ShoppingCartViewModelFactory(private val repository: ShoppingCartRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ShoppingCartRepository::class.java).newInstance(repository)
    }
}