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
}