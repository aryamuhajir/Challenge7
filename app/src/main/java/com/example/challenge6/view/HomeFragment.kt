package com.example.challenge6.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge6.R
import com.example.challenge6.adapter.RvAdapter
import com.example.challenge6.manager.UserManager
import com.example.challenge6.viewmodel.ViewModelFilm
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapterFilm : RvAdapter
    lateinit var userManager : UserManager

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())
        userManager.userNAME.asLiveData().observe(viewLifecycleOwner) {
            txtNama.text = it
        }
        btnFavorite.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
        btnProfil.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_profileFragment)

        }

        getDataFilm()


    }

    fun getDataFilm(){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelFilm::class.java)
        viewModel.getFilmLiveDataObserver().observe(requireActivity()){
            if (it != null){
                rv_item.layoutManager = LinearLayoutManager(requireContext())
                adapterFilm = RvAdapter (){
                    val bund = Bundle()
                    bund.putParcelable("detailfilm", it)
                    view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailFragment, bund)
                }
                rv_item.adapter = adapterFilm
                adapterFilm.setDataFilm(it)
                adapterFilm.notifyDataSetChanged()
                }

        }
        viewModel.getDataFilmLive()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}