package com.bytebyte6.skip.data

import android.content.Context
import androidx.room.*

@Database(entities = [Log::class,Account::class,Sport::class,SportPlan::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun logDao(): LogDao

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var appDataBase: AppDataBase? = null

        fun getAppDataBase(context: Context): AppDataBase {
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
            return appDataBase!!
        }
    }
}

