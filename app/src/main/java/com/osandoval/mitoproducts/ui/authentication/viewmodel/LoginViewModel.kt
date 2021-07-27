package com.osandoval.mitoproducts.ui.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.domain.authentication.login.ILoginRepository
import com.osandoval.mitoproducts.utils.sharedpreferences.ISharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val repository: ILoginRepository, private val sharedPreferences: ISharedPreferences) : ViewModel(){
    fun validateUser(username: String, password: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())

        try {
            val user = sharedPreferences.getUser()
            if( user != "") {
                Log.d("meh", "validateUser: $user")
                emit(Resource.Success(true))
            }else {
                val response = repository.validateUser(username, password).status
                if (response) sharedPreferences.saveUser(username)
                emit(Resource.Success(response))
            }
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun findUser() : Boolean  {
        var result = false
        viewModelScope.launch {
           if( sharedPreferences.getUser() != "") result = true
        }
        return result
    }

}

class LoginViewModelFactory(private val repository: ILoginRepository, private val sharedPreferences: ISharedPreferences)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ILoginRepository::class.java,
                                         ISharedPreferences::class.java)
                         .newInstance(repository,sharedPreferences)
    }
}