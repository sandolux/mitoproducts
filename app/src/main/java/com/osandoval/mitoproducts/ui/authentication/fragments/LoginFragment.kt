package com.osandoval.mitoproducts.ui.authentication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.osandoval.mitoproducts.MainActivity
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.remote.authentication.RemoteLoginDataSource
import com.osandoval.mitoproducts.data.remote.RetrofitClient
import com.osandoval.mitoproducts.databinding.FragmentLoginBinding
import com.osandoval.mitoproducts.domain.login.LoginRepository
import com.osandoval.mitoproducts.ui.authentication.viewmodel.LoginViewModel
import com.osandoval.mitoproducts.ui.authentication.viewmodel.LoginViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val TAG ="Meh"
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>{
        LoginViewModelFactory(
            LoginRepository(
                RemoteLoginDataSource(RetrofitClient.webService)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding = FragmentLoginBinding.bind(view)

        setButtonLoginListener()
        setUserRegisterListener()
    }

    private fun setUserRegisterListener() {
        binding.textViewRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun setButtonLoginListener() {
        binding.buttonLogin.setOnClickListener {
            val username = binding.textInputUsername.text.toString()
            val password = binding.textInputPassword.text.toString()

            viewModel.validateUser(username, password).observe(viewLifecycleOwner, {result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.d(TAG, "onViewCreated: LOADING...")
                    }
                    is Resource.Success -> {
                        when(result.data) {
                            true -> {
                                startActivity(Intent(activity,MainActivity::class.java))
                            }
                            false ->{
                                Log.d(TAG, "onViewCreated: error de credenciales")
                            }
                        }
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "onViewCreated: ${result.exception}")
                    }
                }
            })
        }
    }

}