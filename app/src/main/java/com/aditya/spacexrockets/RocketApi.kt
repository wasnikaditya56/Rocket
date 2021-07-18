package com.aditya.spacexrockets

import retrofit2.http.GET

interface RocketApi {

    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v4/"
    }

    @GET("rockets")
    suspend fun getRockets(): List<Rocket>
}