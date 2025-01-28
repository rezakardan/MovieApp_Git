package com.example.movieapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteTable")
data class FavoriteEntity (

    @PrimaryKey
    var id:String="0",
    var image:String?=null,
    var title:String?=null,
    var date:String?=null,
    var rating:String?=null,







)