package com.example.challenge6.repository

import androidx.lifecycle.MutableLiveData
import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.model.ResponseRegister
import com.example.challenge6.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {
    fun loginRepo(email : String, password : String, liveData : MutableLiveData<GetAllUserResponse>) {
        val call : Call<GetAllUserResponse> = apiService.loginUser(email, password)
        call?.enqueue(object  : Callback<GetAllUserResponse>{
            override fun onResponse(
                call: Call<GetAllUserResponse>,
                response: Response<GetAllUserResponse>
            ) {
                if (response.isSuccessful){
                    if (response.code().toString().equals("404")){
                        liveData.postValue(null)

                    }else{
                        liveData.postValue(response.body())
                    }

                }else{
                    liveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<GetAllUserResponse>, t: Throwable) {
                liveData.postValue(null)

            }

        })

    }
    fun registerRepo(username : String, email :String, password: String, liveData: MutableLiveData<ResponseRegister>){
        val call : Call<ResponseRegister> = apiService.registerUser(username ,email, password)
        call?.enqueue(object : Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.isSuccessful){
                    liveData.postValue(response.body())
                }else{
                    liveData.postValue(null)


                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                liveData.postValue(null)
            }

        })

    }
    fun updateRepo(id : Int, username: String, namaLengkap : String, alamat : String, tanggal : String, liveData: MutableLiveData<GetAllUserResponse>){
        val call : Call<GetAllUserResponse> = apiService.updateUser(id, username, namaLengkap, alamat,tanggal)
        call?.enqueue(object : Callback<GetAllUserResponse>{
            override fun onResponse(
                call: Call<GetAllUserResponse>,
                response: Response<GetAllUserResponse>
            ) {
                if (response.isSuccessful){
                    liveData.postValue(response.body())
                }else{
                    liveData.postValue(null)

                }
            }

            override fun onFailure(call: Call<GetAllUserResponse>, t: Throwable) {
                liveData.postValue(GetAllUserResponse(alamat, namaLengkap, tanggal, "", "", "",username))
            }

        })

    }
}