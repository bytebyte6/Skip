package com.bytebyte6.skip.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LogDao {
    @Insert
    fun insert(log: Log)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(log: Log)

    @Query("SELECT * FROM Log WHERE packageName=:key")
    fun get(key: String): Log?

    @Query("SELECT * FROM Log ORDER BY count DESC")
    fun list(): LiveData<List<Log>>
}