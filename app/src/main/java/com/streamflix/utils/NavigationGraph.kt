package com.streamflix.utils

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.medialibrary.model.MediaData
import com.streamflix.ui.screens.home.HomeScreen
import com.streamflix.ui.screens.media_play.MediaPlayScreen
import com.streamflix.ui.screens.movie_detail.MovieDetailScreen
import kotlinx.serialization.json.Json
import java.net.URLDecoder

typealias NavToMediaPlayer = (path: String) -> Unit

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier, navToMediaPlayer: NavToMediaPlayer) {
    NavHost(navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(modifier, navController)
        }
        composable(
            route = "MovieDetailScreen/{mediaData}",
            deepLinks = listOf(navDeepLink {
                uriPattern = "android-app://androidx.navigation/MovieDetailScreen/{mediaData}"
            })
        ) { backStackEntry ->
            val jsonData = backStackEntry.arguments?.getString("mediaData")
            val decodedJson = URLDecoder.decode(jsonData, "UTF-8")
            val mediaItem = Json.decodeFromString<MediaData>(decodedJson)
            MovieDetailScreen(modifier = Modifier, mediaItem, navController) { path ->
                navToMediaPlayer(path)
            }
        }
    }
}