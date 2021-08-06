package com.bossco.spacexclient.database

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bossco.spacexclient.models.Info

/**
 * Created by Don Muthiani on 8/5/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */
@Database(entities = [Info::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        fun getDbInstance(context: Context): AppDatabase {

            val databaseBuilder = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-db"
            ).fallbackToDestructiveMigration()
            return databaseBuilder.build()
        }
    }

    abstract fun getDao(): AppDao

}