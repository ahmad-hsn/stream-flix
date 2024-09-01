package com.streamflix.ui.screens.media_play

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.streamflix.R
import com.streamflix.utils.AppConstants

@Composable
fun MediaPlayScreen(
    path: String?
) {
    var isMediaPlaying by remember { mutableStateOf(false) }

    if (!isMediaPlaying) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = path?.let { "${AppConstants.MEDIA_BASE_URL}w1280$it" }
                            ?: "https://via.placeholder.com/150",
                    ),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clickable {
                            isMediaPlaying = true
                        }
                )
            }
            Text(text = "Click Button To Play", modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp).background
                (color =
            Color.Red), fontSize
            = 20.sp,
                color =
            Color.White)
        }
    } else {
        val context = LocalContext.current
        val exoPlayer = remember {
            ExoPlayer.Builder(context)
                .setMediaSourceFactory(
                    com.google.android.exoplayer2.source.DefaultMediaSourceFactory(
                        DefaultHttpDataSource.Factory()
                    )
                )
                .build().apply {
                    val mediaItem = MediaItem.fromUri(Uri.parse(AppConstants.BONUS_VIDEO_URL))
                    setMediaItem(mediaItem)
                    prepare()
                    playWhenReady = true
                }
        }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}