package com.example.myapplication.sensors_doubletap

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.sensors_doubletap.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometre: Sensor
    private lateinit var binding: ActivityMainBinding

    // Contadores para cada eje
    private var xCount = 0
    private var yCount = 0
    private var zCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Utilizamos bindings para acceder fácilmente a los elementos gráficos
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos el sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)!!
        sensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            // Obtenemos las lecturas del acelerómetro (X, Y, Z)
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Actualizamos las barras de progreso
            binding.xProgressBar.progress = abs((x * 10).toInt())
            binding.yProgressBar.progress = abs((y * 10).toInt())
            binding.zProgressBar.progress = abs((z * 10).toInt())

            // Verificamos si los valores de las barras están en el rango de 85 a 100 y actualizamos los contadores
            if (binding.xProgressBar.progress in 85..100) {
                xCount++
                binding.xCounter.text = xCount.toString()
            }

            if (binding.yProgressBar.progress in 85..100) {
                yCount++
                binding.yCounter.text = yCount.toString()
            }

            if (binding.zProgressBar.progress in 85..100) {
                zCount++
                binding.zCounter.text = zCount.toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se requiere implementación
    }

    // No olvides desregistrar el listener cuando la actividad se destruya
    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}
