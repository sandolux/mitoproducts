package com.osandoval.mitoproducts.ui.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.core.capitalized
import com.osandoval.mitoproducts.data.model.UserSession
import com.osandoval.mitoproducts.domain.authentication.login.ILoginRepository
import com.osandoval.mitoproducts.domain.authentication.login.LoginRepository
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val repository: LoginRepository, private val sharedPreferences: SharedPreferences) : ViewModel(){
    fun validateUser(username: String, password: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())

        try {
            val response = repository.validateUser(username, password)

            if(response.data != null){
                val user = response.data
                sharedPreferences.saveUser(
                    UserSession(user!!.id.toString(),
                                user.user,
                        user.name.capitalized() + " " + user.lastName.capitalized() ,
                                user.email)
                )
            }

            emit(Resource.Success(response.status))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun findUser() : Boolean  {
        var result = false
        viewModelScope.launch {
           if( sharedPreferences.getUser()?.uid != "") result = true
        }
        return result
    }

}

class LoginViewModelFactory(private val repository: ILoginRepository, private val sharedPreferences: SharedPreferences)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
                                         LoginRepository::class.java,
                                         SharedPreferences::class.java)
                         .newInstance(repository,sharedPreferences)
    }
}