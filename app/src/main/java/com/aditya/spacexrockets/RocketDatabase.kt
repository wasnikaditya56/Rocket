package com.aditya.spacexrockets

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Rocket::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class RocketDatabase : RoomDatabase() {

    abstract fun rocketDao(): RocketDao

    companion object {
        const val DATABASE_NAME = "rocket_database"
    }
}