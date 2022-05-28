package com.example.challenge6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.repository.FavoriteRepository
import com.example.challenge6.repository.FilmRepository
import com.example.challenge6.room.Favorite
import com.example.challenge6.singleliveevent.SingleUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFav @Inject constructor(private val repository : FavoriteRepository) : ViewModel() {

    lateinit var favLiveData: SingleUser<List<Favorite>>
    lateinit var cekData : MutableLiveData<Int>
    lateinit var cek : MutableLiveData<Int>



    init {
        favLiveData = SingleUser()
        cekData = MutableLiveData()
        cek = MutableLiveData()
    }

    fun getFavLiveDataObserver(): SingleUser<List<Favorite>> {
        return favLiveData
    }
    fun getCekDataObserver() : MutableLiveData<Int>{
        return cekData
    }

    fun getAllFavLive(email : String){
        viewModelScope.launch {
            val list = repository.getFavRepo(email)
            delay(100)
            favLiveData.postValue(list)
        }
    }

    fun insertFavLive(fav : Favorite){
        viewModelScope.launch {
            repository.insertFavRepo(fav)
            delay(2000)
            getAllFavLive(fav.email)
        }

    }
    fun deleteFavLive(id : String, email: String){
        viewModelScope.launch {
            repository.deleteFavRepo(id, email)
            delay(2000)
            getAllFavLive(email)

        }
    }
    fun cekFavLiveData(id : String, email: String){
        viewModelScope.launch {
            val cek = repository.cekRepo(id, email)

            cekData.postValue(cek)
        }
    }



}
