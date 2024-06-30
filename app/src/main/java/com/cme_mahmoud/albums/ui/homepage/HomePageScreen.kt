package com.cme_mahmoud.albums.ui.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.cme_mahmoud.common.model.AlbumObject

@Composable
fun HomePageScreen(albums: List<AlbumObject>) {
    AlbumGrid(albums)
}

@Composable
fun AlbumGrid(albums: List<AlbumObject>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(albums) { album ->
            AlbumItem(album)
        }
    }
}

@Composable
fun AlbumItem(album: AlbumObject) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        val painter = rememberImagePainter(data = album.image)

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Album name: ${album.name}")

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Album Artist: ${album.artist}")
    }
}