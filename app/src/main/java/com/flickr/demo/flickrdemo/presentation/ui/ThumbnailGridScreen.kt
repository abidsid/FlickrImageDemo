package com.flickr.demo.flickrdemo.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.flickr.demo.flickrdemo.R
import com.flickr.demo.flickrdemo.data.model.Items
import com.flickr.demo.flickrdemo.presentation.viewmodel.ThumbnailViewModel

@Composable
fun ThumbnailGridScreen(
    viewModel: ThumbnailViewModel = viewModel(),
    navController: NavController
) {
    /* val viewModel: ThumbnailViewModel = viewModel(
         factory = ThumbnailViewModelFactory(repository)  // Pass the required repository
     )*/
    val thumbnails by viewModel.thumbnails.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .semantics { // Add semantics to the entire screen
            contentDescription = "Search screen"
        }) {
        SearchBar(
            query = searchQuery,
            onQueryChanged = { query -> viewModel.onSearchQueryChanged(query) },
            onSearch = { query -> viewModel.searchThumbnails(query) }
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Material loading indicator
            }
        } else if (thumbnails != null &&
                thumbnails?.items != null &&
                thumbnails!!.items.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
            ) {
                items(thumbnails!!.items) { item ->
                    ThumbnailItem(item) {
                        viewModel.setSelectedThumbnail(item) // Cache the clicked item
                        navController.navigate("details")
                    }
                }
            }
        }

//        if (thumbnails != null && thumbnails?.items!!.isNotEmpty()) {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3), // Adjust to the number of items per row
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(thumbnails?.items!!) { items ->
//                    ThumbnailItem(items)
//                }
//            }
//        }

    }
}

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, onSearch: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.2f))
            .clip(RoundedCornerShape(8.dp))
            .semantics { // Add semantics to the entire screen
                contentDescription = "Searching"
            },
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        IconButton(onClick = { onSearch(query) }) {
            Icon(Icons.Default.Search, contentDescription = "Search")
        }
    }
}

@Composable
fun ThumbnailItem(
    thumbnail: Items,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .semantics { // Add semantics to the entire screen
                contentDescription = "Thumbnail Item ${thumbnail.title}"
            },
        shape = RoundedCornerShape(8.dp),

        ) {
        Log.d("Images>>", "ThumbnailItem: ${thumbnail.link}")
        Column {
            var imageUrl = thumbnail.media.m

            AsyncImage(
                model = imageUrl,
                placeholder = painterResource(id = R.drawable.images),
                contentDescription = thumbnail.title,
                modifier = Modifier.size(150.dp) // Optional: Set a size for the image
            )

            Text(
                text = thumbnail.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
