package com.bash.shoestore.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bash.shoestore.screens.LoginFragmentDirections
import com.bash.shoestore.R
import com.bash.shoestore.databinding.FragmentLoginBinding
import com.bash.shoestore.model.LoginState
import com.bash.shoestore.model.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.singIn.setOnClickListener {
            viewModel.onLogin(binding.editTextTextPersonName.text.toString(),
                binding.editTextNumberPassword.text.toString())
        }

        binding.singUp.setOnClickListener { view: View ->
            viewModel.onRegister(binding.editTextTextPersonName.text.toString(),
                binding.editTextNumberPassword.text.toString())
        }

        viewModel.loginState.observe(this as LifecycleOwner, Observer { ls ->
            when (ls) {
                LoginState.REGISTER -> {
                    navigateToWelcome()
                    viewModel.onEventLoginComplete()
                }
                LoginState.LOGIN -> {
                    navigateToListing()
                    viewModel.onEventLoginComplete()
                }
            }
        })

        return binding.root
    }

    private fun navigateToWelcome() {
        val action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(
            viewModel.emailText.value ?: ""
        )
        findNavController().navigate(action)
    }

    private fun navigateToListing() {
        val action = LoginFragmentDirections.actionLoginFragmentToListingFragment()
        findNavController().navigate(action)
    }
}