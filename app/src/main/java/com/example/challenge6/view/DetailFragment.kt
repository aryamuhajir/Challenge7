package com.example.challenge6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.challenge6.R
import com.example.challenge6.manager.UserManager
import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.room.Favorite
import com.example.challenge6.singleliveevent.SingleUser
import com.example.challenge6.viewmodel.ViewModelFav
import com.example.challenge6.viewmodel.ViewModelLogin
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var userManager : UserManager
    private var cekA = 0


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
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserManager(requireContext())

        val detailfilm = arguments?.getParcelable<GetAllFilmResponseItem>("detailfilm")

        txtJudul.text = detailfilm?.title
        txtTanggal.text = detailfilm?.releaseDate
        txtSutradara.text = detailfilm?.director
        txtSinopsis.text = detailfilm?.synopsis

        Glide.with(requireActivity()).load(detailfilm?.image).into(imageFilm1)


        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelLogin::class.java)
        viewModel.dataUser(requireContext()).observe(viewLifecycleOwner){
            txtJudul.hint = it
            cekBintang(detailfilm!!.id, it)
        }





        btnFavorite2.setOnClickListener {
                fav(Favorite(null, txtJudul.hint.toString(),
                    detailfilm!!.createdAt,
                    detailfilm!!.director,
                    detailfilm!!.id,
                    detailfilm!!.image,
                    detailfilm!!.releaseDate, detailfilm!!.synopsis, detailfilm!!.title))




         }
    }
    fun cekBintang(id : String, email : String){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelFav::class.java)
        viewModel.getCekDataObserver().observe(requireActivity()) {
            if (it != 0){
                viewModel.cek.postValue(1)
            }else{

                viewModel.cek.postValue(0)
            }
        }
        viewModel.cek.observe(viewLifecycleOwner){
            if (it == 1){

                cekA = 1
                btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_24)

            }else{
                cekA = 0
                btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
            }
        }
        viewModel.cekFavLiveData(id, email)



    }
    fun fav(favorite: Favorite){
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModelFav::class.java)

        if (cekA == 0){
            GlobalScope.async {
                viewModel.insertFavLive(favorite)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Berhasil menambahkan ke Favorite Film", Toast.LENGTH_LONG).show()
                    btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                }


            }
        }else{
            GlobalScope.async {
                viewModel.deleteFavLive(favorite.id, favorite.email)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Berhasil menghapus dari Favorite Film", Toast.LENGTH_LONG).show()
                    btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_border_24)

                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}