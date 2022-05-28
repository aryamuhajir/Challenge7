package com.example.challenge6.repository

import androidx.lifecycle.MutableLiveData
import com.example.challenge6.network.ApiService
import com.example.challenge6.room.Favorite
import com.example.challenge6.room.FavoriteDao
import com.example.challenge6.singleliveevent.SingleUser
import javax.inject.Inject

class FavoriteRepository  @Inject constructor(private val dao: FavoriteDao) {
    suspend fun getFavRepo(email: String) : List<Favorite>{
        return dao.getFav(email)
    }
    suspend fun insertFavRepo(fav : Favorite){
        dao.insertFavorite(fav)

    }
    suspend fun deleteFavRepo(id: String, email: String){
        dao.deleteFavoriteFilmById(id, email)
    }
    suspend fun cekRepo(id : String, email: String) : Int {
        return dao.cekFilm(id, email)
    }
}