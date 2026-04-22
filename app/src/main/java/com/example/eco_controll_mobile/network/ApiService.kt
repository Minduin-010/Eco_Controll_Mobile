package com.example.eco_controll_mobile.network

import com.example.eco_controll_mobile.ui.features.auth.LoginRequest
import com.example.eco_controll_mobile.ui.features.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}