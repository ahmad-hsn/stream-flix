package com.streamflix.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.medialibrary.model.MediaData

@Composable
fun MediaCarousels(modifier: Modifier, categorizedResults: Map<String, List<MediaData>>, onItemClick: OnItemClick) {
    categorizedResults.forEach { (mediaType, mediaItems) ->
        Text(
            text = mediaType.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            items(mediaItems) { item ->
                CarouselItem(mediaData = item, modifier = Modifier, onItemClick = { clickedItem ->
                    onItemClick(clickedItem)
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}