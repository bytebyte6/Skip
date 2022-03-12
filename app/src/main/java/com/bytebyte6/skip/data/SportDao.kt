package com.bytebyte6.skip.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SportDao {
    @Insert
    fun insert(sport: Sport)

    @Insert
    fun insert(sports: List<Sport>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(sport: Sport)

    @Query("SELECT * FROM Sport WHERE id=:id")
    fun get(id: Int): Sport?

    @Query("SELECT * FROM Sport")
    fun list(): LiveData<List<Sport>>

    @Query("SELECT * FROM Sport")
    fun getList(): List<Sport>

    @Query("SELECT COUNT(id) FROM Sport")
    fun getCount(): Int

    @Query("SELECT MAX(id) FROM Sport")
    fun maxId(): Int

    @Query("SELECT * FROM Sport ORDER BY RANDOM() LIMIT 5")
    fun random(): List<Sport>
}