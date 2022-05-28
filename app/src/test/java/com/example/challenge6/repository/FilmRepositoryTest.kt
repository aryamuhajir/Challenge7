package com.example.challenge6.repository

import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.network.ApiService
import com.example.challenge6.singleliveevent.SingleUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class FilmRepositoryTest {
    private lateinit var apiService : ApiService
    private lateinit var repository: FilmRepository

    private lateinit var liveData : SingleUser<List<GetAllFilmResponseItem>>

    @Before
    fun setUp() {
        apiService = mockk()
        repository = FilmRepository(apiService)
        liveData = SingleUser()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getFilmRepo() : Unit  = runBlocking {
        val response = mockk<Call<List<GetAllFilmResponseItem>>>(relaxed = true)
        every {
            runBlocking {
                apiService.getAllFilm()
            }
        } returns response

        repository.getFilmRepo(liveData)

        verify {
            runBlocking {
                apiService.getAllFilm()
            }
        }
    }
}