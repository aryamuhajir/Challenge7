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
import com.example.challenge6.adapter.FavAdapter
import com.example.challenge6.manager.UserManager
import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.viewmodel.ViewModelFav
import com.example.challenge6.viewmodel.ViewModelFilm
import kotlinx.android.synthetic.main.fragment_favorite.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var adapterFavorite : FavAdapter
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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())
        getFav()

    }
    fun getFav(){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelFav::class.java)
        viewModel.getFavLiveDataObserver().observe(viewLifecycleOwner){

                if (it.size >= 1){
                    rv_fav.layoutManager = LinearLayoutManager(requireContext())
                    adapterFavorite= FavAdapter (){
                        val bund = Bundle()
                        bund.putParcelable("detailfilm", GetAllFilmResponseItem(it.createdAt, it.director, it.id,it.image, it.releaseDate, it.synopsis, it.title))
                        view?.findNavController()?.navigate(R.id.action_favoriteFragment_to_detailFragment, bund)

                    }
                    rv_fav.adapter = adapterFavorite
                    adapterFavorite.setDataFilm(it!!)
                    adapterFavorite.notifyDataSetChanged()
                    rv_fav.visibility = View.VISIBLE
                }else{
                    rv_fav.visibility = View.INVISIBLE
                    statusTxt.text = "BELUM ADA FILM FAVORITE"
                }

        }
        userManager.userEMAIL.asLiveData().observe(requireActivity()){
            viewModel.getAllFavLive(it)
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}