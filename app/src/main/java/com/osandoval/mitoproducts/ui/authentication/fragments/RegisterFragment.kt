package com.osandoval.mitoproducts.ui.authentication.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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
    private val TAG = "APP_MITOPRODUCT"
    private val ORIGIN = "[REGISTER_FRAGMENT]"

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
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
        setInputOnChanged()
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

            viewModel.signUp(username, name, lastname, address, phone, email, password)
                .observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is Resource.Loading -> {
                            Log.d(TAG, "$ORIGIN onViewCreated: LOADING...")
                        }
                        is Resource.Success -> {
                            Toast.makeText(context,"Registro exitoso",Toast.LENGTH_LONG).show()
                            goToSignIn(username, password)
                        }
                        is Resource.Failure -> {
                            Log.d(TAG, "$ORIGIN onViewCreated: ${result.exception}")
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

    private fun goToSignIn(username: String, password: String) {
        val action =
            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(username, password)
        findNavController().navigate(action)
    }

    private fun setInputOnChanged() {
        binding.textInputUsername.doOnTextChanged { text, _, _, _ ->
            usernameLiveData.value = text.toString()
        }

        binding.textInputName.doOnTextChanged { text, _, _, _ ->
            nameLiveData.value = text.toString()
        }

        binding.textInputLastname.doOnTextChanged { text, _, _, _ ->
            lastNameLiveData.value = text.toString()
        }

        binding.textInputAddress.doOnTextChanged { text, _, _, _ ->
            addressLiveData.value = text.toString()
        }

        binding.textInputPhone.doOnTextChanged { text, _, _, _ ->
            phoneLiveData.value = text.toString()
        }

        binding.textInputEmail.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text.toString()
        }

        binding.textInputPassword.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text.toString()
        }

        isValidLiveData.observe(viewLifecycleOwner) { isValid ->
            binding.buttonSignUp.isEnabled = isValid
        }
    }

    private fun validateForm(
        username: String?, name: String?, lastName: String?, address: String?,
        phone: String?, email: String?, password: String?,
    ): Boolean {

        val isValidUsername = username != null && username.isNotBlank()
        val isValidName = name != null && name.isNotBlank()
        val isValidLastName = lastName != null && lastName.isNotBlank()
        val isValidAddress = address != null && address.isNotBlank()
        val isValidPhone = phone != null && phone.isNotBlank()
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >= 6

        return isValidUsername && isValidName && isValidLastName && isValidAddress && isValidPhone && isValidEmail && isValidPassword
    }

    // LIVEDATA
    private val usernameLiveData = MutableLiveData<String>()
    private val nameLiveData = MutableLiveData<String>()
    private val lastNameLiveData = MutableLiveData<String>()
    private val addressLiveData = MutableLiveData<String>()
    private val phoneLiveData = MutableLiveData<String>()
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false

        addSource(usernameLiveData){ username ->
            val name = nameLiveData.value
            val lastName = lastNameLiveData.value
            val address = addressLiveData.value
            val phone = phoneLiveData.value
            val email = emailLiveData.value

            val password = passwordLiveData.value
            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }

        addSource(nameLiveData){ name ->
            val username = usernameLiveData.value
            val lastName = lastNameLiveData.value
            val address = addressLiveData.value
            val phone = phoneLiveData.value
            val email = emailLiveData.value
            val password = passwordLiveData.value

            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }

        addSource(lastNameLiveData){ lastName ->
            val username = usernameLiveData.value
            val name = nameLiveData.value
            val address = addressLiveData.value
            val phone = phoneLiveData.value
            val email = emailLiveData.value
            val password = passwordLiveData.value

            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }

        addSource(addressLiveData){ address ->
            val username = usernameLiveData.value
            val name = nameLiveData.value
            val lastName = lastNameLiveData.value
            val phone = phoneLiveData.value
            val email = emailLiveData.value
            val password = passwordLiveData.value

            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }

        addSource(phoneLiveData){ phone ->
            val username = usernameLiveData.value
            val name = nameLiveData.value
            val lastName = lastNameLiveData.value
            val address = addressLiveData.value
            val email = emailLiveData.value
            val password = passwordLiveData.value

            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }

        addSource(emailLiveData){ email ->
            val username = usernameLiveData.value
            val name = nameLiveData.value
            val lastName = lastNameLiveData.value
            val address = addressLiveData.value
            val phone = phoneLiveData.value
            val password = passwordLiveData.value

            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }

        addSource(passwordLiveData){ password ->
            val username = usernameLiveData.value
            val name = nameLiveData.value
            val lastName = lastNameLiveData.value
            val address = addressLiveData.value
            val phone = phoneLiveData.value
            val email = emailLiveData.value

            this.value = validateForm(username,name,lastName,address,phone,email,password)
        }
    }
}