package com.streamflix.ui.screens

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.streamflix.ui.screens.media_play.MediaPlayScreen

class MediaPlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val data = intent.getStringExtra("path")
        setContent{
            MediaPlayScreen(path = data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}