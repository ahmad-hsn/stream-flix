package com.streamflix.ui.screens.movie_detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.medialibrary.model.KnownFor
import com.medialibrary.model.MediaData
import com.streamflix.R
import com.streamflix.ui.screens.home.CarouselItem
import com.streamflix.utils.AppConstants
import com.streamflix.utils.NavToMediaPlayer
import com.streamflix.utils.getYearFromDataString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MovieDetailScreen(
    modifier: Modifier,
    mediaData: MediaData,
    navController: NavController,
    navToMediaPlayer: NavToMediaPlayer
) {
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val scrollState = rememberLazyListState()
    val imageHeightPx = with(LocalDensity.current) { 400.dp.toPx() }
    val overlayHeightRatio = 0.3f.coerceIn(0f, 1f)
    mediaData.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = it.posterPath?.let { "${AppConstants.MEDIA_BASE_URL}w500$it" }
                                ?: "https://via.placeholder.com/150",
                        ),
                        contentDescription = "",
                        modifier = imageModifier
                            .graphicsLayer(
                                scaleX = 1 - (scrollState.firstVisibleItemScrollOffset / imageHeightPx) * 0.5f,
                                scaleY = 1 - (scrollState.firstVisibleItemScrollOffset / imageHeightPx) * 0.5f,
                                translationX = -(scrollState.firstVisibleItemScrollOffset / imageHeightPx) * 100f,
                                translationY = 0f
                            ),
                        contentScale = ContentScale.Crop
                    )
                    if (it.mediaType != AppConstants.PERSON) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "",
                            modifier = Modifier
                                .clickable {
                                    val encodedPath = URLEncoder.encode(it.posterPath, StandardCharsets.UTF_8.toString())
                                    navToMediaPlayer(encodedPath)
                                }
                                .width(80.dp)
                                .height(80.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp * overlayHeightRatio) // Overlay height as a fraction of image height
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 0f,
                                endY = (700.dp * overlayHeightRatio).value
                            )
                        )
                        .align(Alignment.BottomCenter) // Position the overlay at the bottom of the image
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Text(
                        text = it.name ?: it.originalName ?: "No Data Found (${"2024-22-05".getYearFromDataString()})",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Row {
                        Chip(text = "")
                        Spacer(modifier = Modifier.width(8.dp))
                        Chip(text = "")
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(2.dp)
                    .width((screenWidth.value - 30).dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${if (it.voteAverage != null) String.format("%.1f", it.voteAverage).toDouble() else "N/A"}",
                            color = Color.White, fontSize = 20.sp
                        )
                        Text(text = if (it.voteAverage != null) " / 10" else "", color = Color.White, fontSize = 16.sp)
                    }
                }
                VerticalDivider(color = Color.White, thickness = 1.dp, modifier = Modifier.height(50.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Text(text = it.mediaType ?: "", color = Color.White, fontSize = 20.sp)
                }
                VerticalDivider(color = Color.White, thickness = 1.dp, modifier = Modifier.height(50.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = if (it.mediaType == AppConstants.PERSON) Icons.Default.Person else Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Text(
                        text = if (it.mediaType == AppConstants.PERSON) if (it.gender == 0) "Female" else "Male" else it.originalLanguage
                            ?: "",
                        color = Color
                            .White,
                        fontSize = 20.sp
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .height(2.dp)
                    .width((screenWidth.value - 30).dp)
            )
            if(it.mediaType == AppConstants.PERSON) {
                it.knownFor?.let { personMedia ->
                    personList(personMedia, navToMediaPlayer)
                }
            } else {
                Row(
                    modifier = Modifier
                        .background(color = Color.Black)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = it.posterPath?.let { "${AppConstants.MEDIA_BASE_URL}w500$it" }
                                ?: "https://via.placeholder.com/150",
                        ), contentDescription = "", modifier = Modifier
                            .height(150.dp)
                            .width(80.dp))
                    Spacer(modifier = modifier.width(5.dp))
                    Text(
                        text = it.overview ?: "Not Available",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier
                            .height(150.dp)
                            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
                    )
                }
            }
        }
    } ?: run {
        Log.e("MovieDetailScreen", "MediaData is null")
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize(),
        shape = CircleShape,
        color = Color.Gray.copy(alpha = 0.7f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun personList(personMedia: List<KnownFor>, navToMediaPlayer: NavToMediaPlayer) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(personMedia) { item ->
            if(item.backdropPath != null) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = item.posterPath?.let {
                                    "${AppConstants.MEDIA_BASE_URL}w200$it"
                                }
                                    ?: "https://via.placeholder.com/150",
                            ), contentDescription = "item poster",
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop)
                        Image(
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "",
                            modifier = Modifier
                                .clickable {
                                    val encodedPath = URLEncoder.encode(item.posterPath, StandardCharsets.UTF_8.toString())
                                    navToMediaPlayer(encodedPath)
                                }
                                .width(80.dp)
                                .height(80.dp)
                        )
                    }
                    Text(text = item.title ?: item.originalTitle ?: "No Title",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.background(color = Color.Red).padding(horizontal = 10.dp).fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}