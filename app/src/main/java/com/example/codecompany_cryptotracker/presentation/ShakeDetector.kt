package com.example.codecompany_cryptotracker.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codecompany_cryptotracker.data.model.WatchListData
import kotlinx.coroutines.delay

class ShakeDetector(private val onShakeDetected: () -> Unit) : SensorEventListener {
    private val shakeThreshold = 1000
    private var lastUpdate: Long = 0
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastUpdate) > 100) {
                val diffTime = currentTime - lastUpdate
                lastUpdate = currentTime

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val speed = (x + y + z - lastX - lastY - lastZ) / diffTime * 10000

                if (speed > shakeThreshold) {
                    onShakeDetected()
                }

                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }
}


@Composable
fun CoinTossApp(watchList: WatchListData) {
    val context = LocalContext.current
    var coinAdded by remember { mutableStateOf(false) }
    var isShaking by remember { mutableStateOf(false) }
    var coinList by remember {
        mutableStateOf<List<String>>(emptyList())
    }
    coinList = listOf("bitcoin", "ethereum", "tether", "solana", "ripple")
//    val shakeThreshold = 1000

    // Shake detection setup
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val shakeDetector = ShakeDetector {
        watchList.addCoinId("bitcoin")
        coinAdded = true
    }

    // Register the shake listener when the composable is created
    DisposableEffect(Unit) {
        sensorManager.registerListener(shakeDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            // Unregister the listener when the composable is removed
            sensorManager.unregisterListener(shakeDetector)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Apply padding to ensure content isn't obscured by app bars
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!coinAdded) {
            Text(
                text = "There is no coin in your watch list. Toss a coin (Shake your phone) and come back again",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )

            val animationProgress by animateFloatAsState(
                targetValue = if (isShaking) 1f else 0f
            )

            CoinAnimation(
                modifier = Modifier.size(100.dp),
                progress = animationProgress
            )

            // Start detecting shake motion
            LaunchedEffect(isShaking) {
                if (isShaking) {
                    delay(500) // Delay for a short time to prevent multiple shakes
                    watchList.addCoinId(coinList.random())
                    coinAdded = true
                }
            }
        } else {
            Text(
                text = "Coin added to watch list!",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}



@Composable
fun CoinAnimation(modifier: Modifier = Modifier, progress: Float) {
    Box(
        modifier = modifier.background(color = Color.Blue, shape = RoundedCornerShape(percent = 50))
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(y = with(LocalDensity.current) { (-100).dp * progress })
                .background(color = Color.Yellow, shape = RoundedCornerShape(percent = 50))
        )
    }
}


