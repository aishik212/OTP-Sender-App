package com.example.basiccontactappwithotpsender.model


import com.google.gson.annotations.SerializedName

data class ContactsGson(
    @SerializedName("contacts")
    val contacts: List<Contact>
)