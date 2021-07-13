package com.example.sensors_notifications

import android.app.Application
import android.os.Looper
import androidx.lifecycle.*
import com.example.sensors_notifications.room.RoomDb
import com.example.sensors_notifications.room.TextEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler
import kotlin.concurrent.thread

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val app = getApplication<Application>()
    private val _LightValue = MutableLiveData<Float>()
    private lateinit var db: RoomDb

    val lightValue: LiveData<String>
    get() = Transformations.map(_LightValue) {
        "%.2f".format(it)
    }
    init {
        db = RoomDb.getDatabase(app)
    }
    fun setLightValue(newValue: Float) {
        _LightValue.value = newValue
    }

    private val _accelValue = MutableLiveData<Float>()
    val accelValue: LiveData<String>
    get() = Transformations.map(_accelValue) {
        "%.2f".format(it)
    }
    fun setAccelValue(newValue: Float) {
        _accelValue.value = newValue
    }
    val textList = db.roomDao().getAllText()
//    fun getText(): LiveData<List<TextEntity>>? {
//        var textList: LiveData<List<TextEntity>>? = null
//        GlobalScope.launch {
//            textList = db.roomDao().getAllText()
//
//        }
//        return textList
//    }

    fun addText(text: String) {
        GlobalScope.launch {
            db.roomDao().addText(TextEntity(text = text))
        }
    }
}