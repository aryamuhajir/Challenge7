package com.example.challenge6.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var idFav : Int?,
    @ColumnInfo(name = "email")
    var email : String,
    @ColumnInfo(name =  "createdAt")
    var createdAt: String,
    @ColumnInfo( name ="director")
    var director: String,
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo( name ="synopsis")
    var synopsis: String,
    @ColumnInfo(name ="title")
    var title: String
) : Parcelable
