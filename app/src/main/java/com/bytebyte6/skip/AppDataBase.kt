package com.bytebyte6.skip

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var appDataBase: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase? {
            if (appDataBase == null) {
                synchronized(AppDataBase::class) {
                    if (appDataBase == null) {
                        appDataBase = Room.databaseBuilder(
                            context,
                            AppDataBase::class.java,
                            "skip.db"
                        ).build()
                    }
                }
            }
            return appDataBase
        }
    }
}

