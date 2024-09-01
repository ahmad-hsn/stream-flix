package com.streamflix.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.streamflix.ui.components.StreamBar
import com.streamflix.ui.theme.StreamFlixTheme
import com.streamflix.utils.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            StreamFlixTheme {
                Scaffold(
                    topBar = { StreamBar("StreamFlix") },
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding)) { path ->
                            navToMediaPlayerScreen(path)
                        }
                    }
                )
            }
        }
    }

    fun navToMediaPlayerScreen(data: String) {
        startActivity(Intent(this, MediaPlayActivity::class.java).apply {
            putExtra("path", data)
        })
    }
}