package com.example.sensors_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sensors_notifications.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var viewModel: MainViewModel
    private var lightSensor: Sensor? = null
    private var stepCountSensor: Sensor? = null
    private var accelerometerSensor: Sensor? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.recycler.adapter = TextAdapter()
        binding.btnSubmit.setOnClickListener {
            val text = binding.textInput.text.toString()
            viewModel.addText(text)
        }
        viewModel.textList.observe(this, Observer {
            (binding.recycler.adapter as TextAdapter).submitList(it)
        })
        createNotifChannel()
//        getData()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }


    override fun onResume() {
        super.onResume()
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI)

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)

        stepCountSensor
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        var i = 0
        event?.let { eventResult ->

            when(eventResult.sensor){
                lightSensor -> {
                    viewModel.setLightValue(eventResult.values[0])
                    createNotif("%.2f".format(eventResult.values[0]))
                }
                accelerometerSensor -> {
                    viewModel.setAccelValue(eventResult.values[0])
                    Log.i("accel", eventResult.values[1].toString())
                    Log.i("accel", eventResult.values[2].toString())
                }

                else -> {
                    Log.i("hello","type ${event.sensor}")
                }
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("Channel 1","Channel 1", NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    fun createNotif(text: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 100, intent, 0)
        val builder = NotificationCompat.Builder(this,"Channel 1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle("Light")
            .setContentText(text)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(1,builder.build())
        }
    }
}