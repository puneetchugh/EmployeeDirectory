package com.example.employeedirectory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    @SerialName("biography")
    val biography: String,
    @SerialName("email_address")
    val emailAddress: String,
    @SerialName("employee_type")
    val employeeType: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("photo_url_large")
    val photoURLLarge: String,
    @SerialName("photo_url_small")
    val photoURLSmall: String,
    @SerialName("team")
    val team: String,
    @SerialName("uuid")
    val uuid: String
)