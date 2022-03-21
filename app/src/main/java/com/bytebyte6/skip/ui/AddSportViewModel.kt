package com.bytebyte6.skip.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bytebyte6.skip.Event
import com.bytebyte6.skip.data.AppDataBase
import com.bytebyte6.skip.data.Sport
import com.bytebyte6.skip.data.TrainingWay
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddSportViewModel(application: Application) : AndroidViewModel(application) {

    private val executorService: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val error = Any()

    private val name = MutableLiveData<Event<Any>>()
    val nameError: LiveData<Event<Any>> = name

    private val minDuration = MutableLiveData<Event<Any>>()
    val minDurationError: LiveData<Event<Any>> = minDuration

    private val maxDuration = MutableLiveData<Event<Any>>()
    val maxDurationError: LiveData<Event<Any>> = maxDuration

    private val minCount = MutableLiveData<Event<Any>>()
    val minCountError: LiveData<Event<Any>> = minCount

    private val maxCount = MutableLiveData<Event<Any>>()
    val maxCountError: LiveData<Event<Any>> = maxCount

    private val minGroup = MutableLiveData<Event<Any>>()
    val minGroupError: LiveData<Event<Any>> = minGroup

    private val maxGroup = MutableLiveData<Event<Any>>()
    val maxGroupError: LiveData<Event<Any>> = maxGroup

    private val groupRestDuration = MutableLiveData<Event<Any>>()
    val groupRestDurationError: LiveData<Event<Any>> = groupRestDuration

    private val restDuration = MutableLiveData<Event<Any>>()
    val restDurationError: LiveData<Event<Any>> = restDuration

    var sport = Sport()

    fun save(id: Int) {
        var pass = true
        if (sport.name.isEmpty()) {
            name.value = Event(error)
            pass = false
        }
        if (sport.restDuration == -1) {
            restDuration.value = Event(error)
            pass = false
        }
        if (sport.trainingWay == TrainingWay.BY_TIME) {
            if (sport.minDuration == -1) {
                minDuration.value = Event(error)
                pass = false
            }
            if (sport.maxDuration == -1) {
                maxDuration.value = Event(error)
                pass = false
            }
        } else {
            if (sport.minCount == -1) {
                minCount.value = Event(error)
                pass = false
            }
            if (sport.maxCount == -1) {
                maxCount.value = Event(error)
                pass = false
            }
            if (sport.minGroup == -1) {
                minGroup.value = Event(error)
                pass = false
            }
            if (sport.maxGroup == -1) {
                maxGroup.value = Event(error)
                pass = false
            }
            if (sport.groupRestDuration == -1) {
                groupRestDuration.value = Event(error)
                pass = false
            }
        }
        if (pass) {
            executorService.execute {
                val sportDao = AppDataBase.getAppDataBase(getApplication())
                    .sportDao()
                if (id == -1) {
                    sportDao.insert(sport)
                } else {
                    sportDao.update(sport)
                }
            }
        }
    }
}