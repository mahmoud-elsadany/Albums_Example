package com.cme_mahmoud.albums.ui.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.cme_mahmoud.common.model.AlbumObject

@Composable
fun HomePageScreen(
    albums: List<AlbumObject>,
    onAlbumClick: (AlbumObject) -> Unit,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onRefresh, modifier = Modifier.padding(16.dp)) {
            Text(text = "Refresh")
        }

        if (albums.isEmpty()) {
            Text(
                text = "There are no albums",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            AlbumGrid(albums, onAlbumClick)
        }

    }
}

@Composable
fun AlbumGrid(albums: List<AlbumObject>, onAlbumClick: (AlbumObject) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(albums) { album ->
            AlbumItem(album, onAlbumClick)
        }
    }
}

@Composable
fun AlbumItem(album: AlbumObject, onAlbumClick: (AlbumObject) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onAlbumClick(album) }
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