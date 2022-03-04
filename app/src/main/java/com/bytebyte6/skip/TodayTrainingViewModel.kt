package com.bytebyte6.skip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.data.Data
import com.bytebyte6.skip.data.SportDao
import com.bytebyte6.skip.data.SportPlan
import java.util.concurrent.Executors

class TodayTrainingViewModel(application: Application) : AndroidViewModel(application) {

    private val appDataBase by lazy { AppDataBase.getAppDataBase(application) }

    private val executorService by lazy { Executors.newSingleThreadExecutor() }

    private val sportPlan = MutableLiveData<SportPlan>()
    val sportPlanUI = sportPlan

    init {
        initSportData()
    }

    private fun initSportData() {
        val sportDao = appDataBase.sportDao()
        executorService.execute {
            if (sportDao.getCount() == 0) {
                sportDao.insert(Data.sports)
            }
            generateTodaySportPlan(sportDao)
        }
    }

    private fun generateTodaySportPlan(sportDao: SportDao) {
        val timeInMillis = getsTheZeroTimeStamp()
        val sportPlanDao = appDataBase.sportPlanDao()
        val sportPlan = sportPlanDao.get(timeInMillis)
        if (sportPlan == null) {
            sportPlanDao.insert(Data.randomSportPlan(sportDao.random()))
            this.sportPlan.postValue(sportPlanDao.get(timeInMillis))
        } else {
            this.sportPlan.postValue(sportPlan)
        }
    }
}