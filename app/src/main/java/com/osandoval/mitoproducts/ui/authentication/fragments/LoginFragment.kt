package com.osandoval.mitoproducts.ui.authentication.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.osandoval.mitoproducts.ui.main.activity.MainActivity
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.authentication.login.LocalLoginDataSource
import com.osandoval.mitoproducts.data.remote.authentication.RemoteLoginDataSource
import com.osandoval.mitoproducts.data.remote.RetrofitClient
import com.osandoval.mitoproducts.databinding.FragmentLoginBinding
import com.osandoval.mitoproducts.domain.authentication.login.LoginRepository
import com.osandoval.mitoproducts.ui.authentication.viewmodel.LoginViewModel
import com.osandoval.mitoproducts.ui.authentication.viewmodel.LoginViewModelFactory
import com.osandoval.mitoproducts.utils.sharedpreferences.SharedPreferences

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val TAG ="Meh"
    private val args by navArgs<LoginFragmentArgs>()
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>{
        LoginViewModelFactory(
            LoginRepository(
                LocalLoginDataSource(AppDatabase.getDatabase(requireContext()).loginDao()),
                RemoteLoginDataSource(RetrofitClient.webService)
            ),
            SharedPreferences(requireContext())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding = FragmentLoginBinding.bind(view)

        when(viewModel.findUser()){
            true -> {
                goToMainActivity()
            }
            false -> {
                if(args.username != "NO_DATA") {
                    binding.textInputUsername.setText(args.username.toString())
                    binding.textInputPassword.setText(args.password.toString())
                }
                setButtonLoginListener()
                setUserRegisterListener()
            }
        }
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
                            true -> { goToMainActivity() }
                            false ->{ Log.d(TAG, "onViewCreated: error de credenciales") }
                        }
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "onViewCreated: ${result.exception}")
                    }
                }
            })
        }
    }

    private fun goToMainActivity(){
        startActivity(Intent(activity, MainActivity::class.java))
    }

}