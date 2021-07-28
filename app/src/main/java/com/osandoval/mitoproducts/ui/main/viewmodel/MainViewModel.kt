package com.osandoval.mitoproducts.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.utils.sharedpreferences.ISharedPreferences
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    fun getUser() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(
                Resource.Success(sharedPreferences.getUser())
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun signOut(){
        sharedPreferences.wipeData()
    }
}

class MainViewModelFactory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SharedPreferences::class.java).newInstance(sharedPreferences)
    }

}