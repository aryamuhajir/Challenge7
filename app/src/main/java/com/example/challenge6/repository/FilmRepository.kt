package com.example.challenge6.repository

import androidx.lifecycle.MutableLiveData
import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.network.ApiService
import com.example.challenge6.singleliveevent.SingleUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FilmRepository @Inject constructor(private val apiService: ApiService) {
    fun getFilmRepo(liveData: SingleUser<List<GetAllFilmResponseItem>>){
        val call : Call<List<GetAllFilmResponseItem>> = apiService.getAllFilm()
        call?.enqueue(object : Callback<List<GetAllFilmResponseItem>>{
            override fun onResponse(
                call: Call<List<GetAllFilmResponseItem>>,
                response: Response<List<GetAllFilmResponseItem>>
            ) {
                if (response.isSuccessful){
                    liveData.postValue(response.body())

                }else{
                    liveData.postValue(null)

                }
            }

            override fun onFailure(call: Call<List<GetAllFilmResponseItem>>, t: Throwable) {
                liveData.postValue(null)

            }

        })
    }
}