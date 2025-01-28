package com.example.movieapp.data.model.detail


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseAllActors(
    @SerializedName("cast")
    var cast: List<Cast>,
    @SerializedName("crew")
    var crew: List<Crew>,
    @SerializedName("id")
    var id: Int
) : Parcelable {
    @Parcelize
    data class Cast(
        @SerializedName("adult")
        var adult: Boolean,
        @SerializedName("cast_id")
        var castId: Int,
        @SerializedName("character")
        var character: String,
        @SerializedName("credit_id")
        var creditId: String,
        @SerializedName("gender")
        var gender: Int,
        @SerializedName("id")
        var id: Int,
        @SerializedName("known_for_department")
        var knownForDepartment: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("order")
        var order: Int,
        @SerializedName("original_name")
        var originalName: String,
        @SerializedName("popularity")
        var popularity: Double,
        @SerializedName("profile_path")
        var profilePath: String?
    ) : Parcelable

    @Parcelize
    data class Crew(
        @SerializedName("adult")
        var adult: Boolean,
        @SerializedName("credit_id")
        var creditId: String,
        @SerializedName("department")
        var department: String,
        @SerializedName("gender")
        var gender: Int,
        @SerializedName("id")
        var id: Int,
        @SerializedName("job")
        var job: String,
        @SerializedName("known_for_department")
        var knownForDepartment: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("original_name")
        var originalName: String,
        @SerializedName("popularity")
        var popularity: Double,
        @SerializedName("profile_path")
        var profilePath: String?
    ) : Parcelable
}