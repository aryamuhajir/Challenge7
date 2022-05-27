package com.example.challenge6.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.model.ResponseRegister
import com.example.challenge6.network.ApiService
import com.example.challenge6.repository.UserRepository
import com.example.challenge6.singleliveevent.SingleUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelUser @Inject constructor(private val repository : UserRepository) : ViewModel(){

    lateinit var userLiveData : SingleUser<GetAllUserResponse>
    lateinit var registerLiveData : MutableLiveData<ResponseRegister>


    init {
        userLiveData = SingleUser()
        registerLiveData = MutableLiveData()
    }
    fun getUserLiveDataObserver() : SingleUser<GetAllUserResponse>{
        return userLiveData
    }


    fun loginLiveData(email : String, password : String){
            repository.loginRepo(email, password, userLiveData)
    }
    fun registerLiveData(username : String, email: String, password: String){
        viewModelScope.launch {
            repository.registerRepo(username,email,password,registerLiveData)
        }
    }
    fun updateLiveData(id : Int, username: String, namaLengkap : String, alamat : String, tanggal : String){
        repository.updateRepo(id, username, namaLengkap, alamat, tanggal, userLiveData)
    }



}