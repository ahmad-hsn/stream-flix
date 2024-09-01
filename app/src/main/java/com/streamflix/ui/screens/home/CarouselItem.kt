package com.streamflix.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.medialibrary.model.MediaData
import com.streamflix.utils.AppConstants

typealias OnItemClick = (mediaData: MediaData) -> Unit

@Composable
fun CarouselItem(mediaData: MediaData, modifier: Modifier, onItemClick: OnItemClick) {
    Column(
        modifier = modifier
            .padding(end = 16.dp)
            .width(150.dp)
            .height(250.dp)
            .clickable { onItemClick(mediaData) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = mediaData.posterPath?.let { "${AppConstants.MEDIA_BASE_URL}w500$it" }
                    ?: "https://via.placeholder.com/150",
            ), contentDescription = "item poster",
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop)
        Spacer(modifier = modifier.height(8.dp))
        Text(text = mediaData.name ?: mediaData.originalName ?: "No Title",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}