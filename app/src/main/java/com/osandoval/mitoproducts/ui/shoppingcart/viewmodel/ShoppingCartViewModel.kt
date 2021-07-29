package com.osandoval.mitoproducts.ui.shoppingcart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.model.toOrderDetailList
import com.osandoval.mitoproducts.domain.shoppingcart.ShoppingCartRepository
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.util.*

class ShoppingCartViewModel(private val repository: ShoppingCartRepository, private val sharedPreferences: SharedPreferences)
    : ViewModel() {
    fun getShoppingCart() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            val user = sharedPreferences.getUser()
            emit(Resource.Success(repository.getShoppingCart(user!!.uid!!.toLong())))
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

    fun insertOrders() = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            val userUID = sharedPreferences.getUser()!!.uid?.toLong()
            val orderUid= UUID.randomUUID().toString()
            repository.insertOrders(repository.getShoppingCart(userUID!!).toOrderDetailList(orderUid), orderUid, userUID)
            repository.wipeShoppingCart(userUID)
            emit(Resource.Success(true))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class ShoppingCartViewModelFactory(private val repository: ShoppingCartRepository, private val sharedPreferences: SharedPreferences)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ShoppingCartRepository::class.java,
            SharedPreferences::class.java
        ).newInstance(repository, sharedPreferences)
    }
}