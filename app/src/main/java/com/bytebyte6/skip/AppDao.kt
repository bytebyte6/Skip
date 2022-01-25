package com.bytebyte6.skip

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppDao {
    @Insert
    fun insert(entity: AppEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: AppEntity)

    @Query("SELECT * FROM AppEntity WHERE packageName=:key")
    fun isExist(key: String): AppEntity?

    @Query("SELECT * FROM AppEntity ORDER BY count DESC")
    fun getLiveData(): LiveData<List<AppEntity>>
}