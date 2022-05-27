package com.example.challenge6.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.challenge6.manager.UserManager
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.singleliveevent.SingleUser

class ViewModelLogin : ViewModel() {
    lateinit var  userManager : UserManager

    fun loginStatus(context: Context) : LiveData<String>{
        userManager = UserManager(context)

        return  userManager.userSTATUS.asLiveData()
    }

    fun  dataUser (context: Context) : LiveData<String>{
        userManager = UserManager(context)

        return userManager.userEMAIL.asLiveData()
    }


}