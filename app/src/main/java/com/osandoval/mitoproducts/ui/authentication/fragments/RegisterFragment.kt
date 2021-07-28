package com.osandoval.mitoproducts.ui.authentication.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.osandoval.mitoproducts.R
import com.osandoval.mitoproducts.core.Resource
import com.osandoval.mitoproducts.data.local.AppDatabase
import com.osandoval.mitoproducts.data.local.authentication.signup.LocalSignUpDataSource
import com.osandoval.mitoproducts.data.remote.RetrofitClient
import com.osandoval.mitoproducts.data.remote.authentication.RemoteSignUpDataSource
import com.osandoval.mitoproducts.databinding.FragmentRegisterBinding
import com.osandoval.mitoproducts.domain.authentication.signUp.SignUpRepository
import com.osandoval.mitoproducts.ui.authentication.viewmodel.RegisterViewModel
import com.osandoval.mitoproducts.ui.authentication.viewmodel.RegisterViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val TAG ="meh"
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>{
        RegisterViewModelFactory(
            SignUpRepository(
                LocalSignUpDataSource(AppDatabase.getDatabase(requireContext()).signUpDao()),
                RemoteSignUpDataSource(RetrofitClient.webService)
            )
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        setHaveAnAccountListener()
        setRegisterButtonListener()
    }

    private fun setRegisterButtonListener() {
        binding.buttonSignUp.setOnClickListener {
            val username = binding.textInputUsername.text.toString()
            val name = binding.textInputName.text.toString()
            val lastname = binding.textInputLastname.text.toString()
            val address = binding.textInputAddress.text.toString()
            val phone = binding.textInputPhone.text.toString()
            val email = binding.textInputEmail.text.toString()
            val password = binding.textInputPassword.text.toString()
            val confirm = binding.textInputPasswordConfirm.text.toString()
            
            viewModel.signUp(username,name,lastname,address,phone,email,password,confirm).observe(viewLifecycleOwner, { result->
                when(result) {
                    is Resource.Loading -> {
                        Log.d(TAG, "onViewCreated: LOADING...")
                    }
                    is Resource.Success -> {
                        goToSignIn(username,password)
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "onViewCreated: ${result.exception}")
                    }
                }
            })
        }
    }

    private fun setHaveAnAccountListener() {
        binding.textViewHaveAnAccount.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun goToSignIn(username: String, password: String){
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(username,password)
        findNavController().navigate(action)
    }
}