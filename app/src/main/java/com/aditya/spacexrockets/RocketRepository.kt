package com.aditya.spacexrockets

import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<Result<List<Rocket>>>

}