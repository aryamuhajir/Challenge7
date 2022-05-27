package com.example.challenge6.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.challenge6.R
import com.example.challenge6.manager.UserManager
import com.example.challenge6.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var userManager: UserManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())

        btnLogin.setOnClickListener {
            var email = editEmail.text.toString()
            var password = editPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank()){
                login(email, password)
            }else{
                Toast.makeText(requireContext(), "Isi email dan password!", Toast.LENGTH_LONG).show()
            }
        }

        txtBlmPunyaAkun.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }
    fun login(email : String, password : String){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelUser::class.java)
        viewModel.getUserLiveDataObserver().observe(viewLifecycleOwner){
            if (it != null){
                GlobalScope.launch {
                    userManager.login(it.username, it.password, it.address, it.completeName, it.dateofbirth, it.email, it.id)
                    userManager.setStatus("yes")
                }

                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                Toast.makeText(requireContext(), "Email atau password salah!", Toast.LENGTH_LONG).show()

            }
        }
        viewModel.loginLiveData(email, password)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}