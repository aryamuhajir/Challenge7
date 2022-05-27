package com.example.challenge6.network

import com.example.challenge6.model.GetAllFilmResponseItem
import com.example.challenge6.model.GetAllUserResponse
import com.example.challenge6.model.ResponseRegister
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("apifilm.php")
    fun getAllFilm() : Call<List<GetAllFilmResponseItem>>
//    @GET("film")
//    suspend fun getAllFilm2() : List<DataFilmBaruItem>

    @POST("register.php/")
    @FormUrlEncoded
    fun registerUser(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("username") username :String
    ) : Call<ResponseRegister>

    @POST("login.php")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<GetAllUserResponse>

    @POST("updateuser.php")
    @FormUrlEncoded
    fun updateUser(
        @Field("id") id : Int,
        @Field("username") username: String,
        @Field("complete_name") completename : String,
        @Field("address") address : String,
        @Field("dateofbirth") dateofbirth : String,
    ) : Call<GetAllUserResponse>


}