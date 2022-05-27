package com.example.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.model.ResponseRegister
import com.example.challenge6.repository.FilmRepository
import com.example.challenge6.repository.UserRepository
import com.example.challenge6.singleliveevent.SingleUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(private val repository : FilmRepository) : ViewModel() {

    lateinit var filmLiveData: SingleUser<List<GetAllFilmResponseItem>>


    init {
        filmLiveData = SingleUser()
    }

    fun getFilmLiveDataObserver(): SingleUser<List<GetAllFilmResponseItem>> {
        return filmLiveData
    }

    fun getDataFilmLive(){
        repository.getFilmRepo(filmLiveData)
    }



}



