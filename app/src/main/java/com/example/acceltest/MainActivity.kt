package com.example.acceltest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random


class MainActivity : AppCompatActivity(), SensorEventListener {

//    values for Sensor
    private lateinit var senSensorManager: SensorManager
    private lateinit var senAccelerometer: Sensor
//    Values for work sensors
    private var lastUpdate: Long = 0
    private var last_X: Float = 0f
    private var last_Y: Float = 0f
    private var last_Z: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        make sensors values
        senSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val mySensor = sensorEvent?.sensor

        if (mySensor!!.type == Sensor.TYPE_ACCELEROMETER){

            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            var currentTime: Long = System.currentTimeMillis()

            if ((currentTime - lastUpdate)>100){
                val difTime = (currentTime-lastUpdate)
                lastUpdate = currentTime
                val speed:Float = Math.abs(x+y+z - last_X -last_Y - last_Z)/difTime*10_000
                if (speed > SHAKE_THRESHOLD){

                }
                last_X=x
                last_Y=y
                last_Z=z
            }
        }
    }

    override fun onAccuracyChanged(Sensor: Sensor?, accuary: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    functions for make new
    override fun onPause(){
        super.onPause()
        senSensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

//    spectial functions
    fun getRandomValues(){
        var generetadNumbers:ArrayList<Int> = arrayListOf(6)
        var cnt:Int = 0
        while (cnt < 6){
            val rnd = Random.nextInt(48)+1
            if (!generetadNumbers.contains(rnd)){
                generetadNumbers.add(rnd)
            } else {
                cnt--
            }
        }

    }

    companion object{
        val SHAKE_THRESHOLD = 600
    }

}

