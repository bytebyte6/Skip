package com.bytebyte6.skip.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SportPlanDao {
    @Insert
    fun insert(sportPlan: SportPlan)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(sportPlan: SportPlan)

    @Query("SELECT * FROM SportPlan WHERE date=:date")
    fun get(date: Long): SportPlan?

    @Query("SELECT * FROM SportPlan")
    fun list(): LiveData<List<SportPlan>>
}