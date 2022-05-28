package com.example.challenge6.repository

import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.model.ResponseRegister
import com.example.challenge6.network.ApiService
import com.example.challenge6.singleliveevent.SingleUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class UserRepositoryTest {
    private lateinit var apiService : ApiService
    private lateinit var repository: UserRepository

    private lateinit var liveData : SingleUser<GetAllUserResponse>
    private lateinit var liveData2 : SingleUser<ResponseRegister>

    private val  email = "rere"
    private val password = "rere"
    private val username = "rere2"
    private val  email2 = "rere2"
    private val password2 = "rere2"

    private val address = "Sidoarjo"
    private val completeName = "MUHAJIR"
    private val dateOfBirth = "8"
    private val id = 4102


    @Before
    fun setUp() {
        apiService = mockk()
        repository = UserRepository(apiService)
        liveData = SingleUser()
        liveData2 = SingleUser()

    }

    @Test
    fun loginRepo() : Unit = runBlocking {
        val response = mockk<Call<GetAllUserResponse>>(relaxed = true)
        every {
            runBlocking {
                apiService.loginUser(email, password)
            }
        } returns response

        repository.loginRepo(email, password, liveData)

        verify {
            runBlocking {
                apiService.loginUser(email, password)
            }
        }
    }


    @Test
    fun registerRepo() {
        val response = mockk<Call<ResponseRegister>>(relaxed = true)
        every {
            runBlocking {
                apiService.registerUser(email2, password2, username)
            }
        } returns response

        repository.registerRepo(email2, password2, username, liveData2)

        verify {
            runBlocking {
                apiService.registerUser(email2, password2, username)
            }
        }
    }

    @Test
    fun updateRepo() {
        val response = mockk<Call<GetAllUserResponse>>(relaxed = true)
        every {
            runBlocking {
                apiService.updateUser(id, username, completeName, address, dateOfBirth)
            }
        } returns response

        repository.updateRepo(id, username, completeName, address, dateOfBirth, liveData)

        verify {
            runBlocking {
                apiService.updateUser(id, username, completeName, address, dateOfBirth)

            }
        }
    }
}