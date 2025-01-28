package com.example.movieapp.data.model.genre


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseGenreList(
    @SerializedName("genres")
    var genres: List<Genre>
) : Parcelable {
    @Parcelize
    data class Genre(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String
    ) : Parcelable
}