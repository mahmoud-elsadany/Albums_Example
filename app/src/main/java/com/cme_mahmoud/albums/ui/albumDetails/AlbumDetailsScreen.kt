package com.cme_mahmoud.albums.ui.albumDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.cme_mahmoud.common.model.AlbumObject

@Composable
fun AlbumDetailsScreen(album: AlbumObject, onOpenStore: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val painter = rememberImagePainter(data = album.image)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Title: ${album.name}")
        Text(text = "Artist: ${album.artist}")
        Text(text = "Genre: ${album.genre}")
        Text(text = "Release Date: ${album.releaseDate}")
        Text(text = "Copyright: ${album.copyright}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onOpenStore) {
            Text(text = "Open in iTunes Store")
        }
    }
}