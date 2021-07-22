package com.osandoval.mitoproducts.ui.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.domain.login.ILoginRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LoginViewModel(private val repository: ILoginRepository) : ViewModel(){
    fun validateUser(username: String, password: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.validateUser(username,password).status))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class LoginViewModelFactory(private val repository: ILoginRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ILoginRepository::class.java).newInstance(repository)
    }
}