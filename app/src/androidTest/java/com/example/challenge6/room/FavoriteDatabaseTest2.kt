package com.example.challenge6.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class FavoriteDatabaseTest2 : TestCase() {
    private lateinit var db : FavoriteDatabase
    private lateinit var dao : FavoriteDao
    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FavoriteDatabase::class.java).build()
        dao = db.favoriteDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun favoriteDao() {
        val result = dao.getFav("rere")
        val result2 = dao.cekFilm("2", "rere")
        val result3 = dao.deleteFavoriteFilmById("3", "rere")
        val result4 = dao.insertFavorite(Favorite(null, "rere", "1324", "james", "3", "\"https:\\/\\/media.matamata.com\\/thumbs\\/2022\\/02\\/16\\/41808-sinopsis-garis-waktuinstagramatfilmgariswaktu-md\\/o-img-41808-sinopsis-garis-waktuinstagramatfilmgariswaktu-md.jpg", "23", "asadasd", "dasdsa"))
    }
}