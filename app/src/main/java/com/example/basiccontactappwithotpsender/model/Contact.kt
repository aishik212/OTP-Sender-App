package com.example.basiccontactappwithotpsender.model


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
) : java.io.Serializable