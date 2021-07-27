package com.osandoval.mitoproducts.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.osandoval.mitoproducts.utils.sharedpreferences.ISharedPreferences

class MainViewModel(private val sharedPreferences: ISharedPreferences) : ViewModel() {
    fun signOut(){
        sharedPreferences.wipeData()
    }
}

class MainViewModelFactory(private val sharedPreferences: ISharedPreferences) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ISharedPreferences::class.java).newInstance(sharedPreferences)
    }

}