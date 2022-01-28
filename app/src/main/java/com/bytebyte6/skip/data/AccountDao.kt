package com.bytebyte6.skip.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {
    @Insert
    fun insert(account: Account)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(account: Account)

    @Query("SELECT * FROM Account WHERE account=:key")
    fun get(key: String): Account?

    @Query("SELECT * FROM Account")
    fun list(): LiveData<List<Account>>
}