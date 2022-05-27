package com.example.challenge6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.challenge6.R
import com.example.challenge6.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDaftar.setOnClickListener {
            var username = editUsername1.text.toString()
            var password2 = editPassword2.text.toString()
            var password = editPassword1.text.toString()
            var email = editPassword1.text.toString()
            if (username.isNotBlank() && password2.isNotBlank() && password.isNotBlank() && email.isNotBlank()){
                if (password.equals(password2)){
                    register(username, email, password)
                }else{
                    Toast.makeText(requireContext(), "Password dan konfirmasi password tidak sesuai!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(requireContext(), "Isi semua form!", Toast.LENGTH_LONG).show()

            }
        }
    }
    fun register(username : String, email : String, password : String){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelUser::class.java)
        viewModel.registerLiveData.observe(requireActivity()){
            if (it != null){
                Toast.makeText(requireContext(), "Berhasil Mendaftar", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "Gagal Mendaftar", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.registerLiveData(username, email, password)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}