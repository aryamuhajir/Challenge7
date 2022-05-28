package com.example.challenge6.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.challenge6.singleliveevent.SingleUser

@Dao
interface FavoriteDao {
    @Insert
    fun insertFavorite(favorite : Favorite) : Long

    @Query("SELECT * FROM Favorite WHERE email = :email")
    fun getFav(email: String): List<Favorite>
    @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE id = :id AND email = :email)")
    fun cekFilm(id: String, email : String) :Int


    @Query("DELETE FROM Favorite WHERE id = :id AND email = :email")
     fun deleteFavoriteFilmById(id: String, email: String)


}