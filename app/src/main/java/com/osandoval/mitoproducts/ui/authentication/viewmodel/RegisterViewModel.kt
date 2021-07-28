package com.osandoval.mitoproducts.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.model.UserSignUp
import com.osandoval.mitoproducts.data.model.toUserEntity
import com.osandoval.mitoproducts.domain.authentication.signUp.ISignUpRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RegisterViewModel(private val repository: ISignUpRepository) : ViewModel(){
    fun signUp(username: String, name: String, lastname: String, address:String,
               phone: String, email: String, password: String, confirmation: String)
        = liveData(viewModelScope.coroutineContext + Dispatchers.Main){

        emit(Resource.Loading())

        try {
            val user = UserSignUp(0,username,name,lastname,address,email,phone,password)
            val result = repository.signUp(user)

            if (result.status){
                repository.saveUser(user.toUserEntity())
                emit(Resource.Success(true))
            }else{
                throw(Exception("Error al crear usuario. ${result.message}"))
            }
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class RegisterViewModelFactory(private val repository: ISignUpRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ISignUpRepository::class.java).newInstance(repository)
    }
}