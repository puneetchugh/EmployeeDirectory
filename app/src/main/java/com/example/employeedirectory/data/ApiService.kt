package com.example.employeedirectory.data

import com.example.employeedirectory.data.model.Employees
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/sq-mobile-interview/employees.json")
    suspend fun fetchEmployees(): Response<Employees>

}