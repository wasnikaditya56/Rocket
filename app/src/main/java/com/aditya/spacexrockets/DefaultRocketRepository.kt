package com.aditya.spacexrockets

import androidx.room.withTransaction
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRocketRepository @Inject constructor(
    private val api: RocketApi,
    private val db: RocketDatabase
) : RocketRepository {
    private val rocketDao = db.rocketDao()

    override fun getRockets() = networkBoundResource(
        query = {
            rocketDao.getRockets()
        },
        fetch = {
            delay(2000)
            api.getRockets()
        },
        saveFetchResult = { rockets ->
            db.withTransaction {
                with(rocketDao) {
                    deleteRockets()
                    insert(rockets)
                }
            }
        }
    )


}