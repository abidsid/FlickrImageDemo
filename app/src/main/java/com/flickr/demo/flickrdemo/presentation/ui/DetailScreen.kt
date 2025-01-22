package com.flickr.demo.flickrdemo.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.flickr.demo.flickrdemo.R
import com.flickr.demo.flickrdemo.data.model.Items
import com.flickr.demo.flickrdemo.presentation.viewmodel.ThumbnailViewModel

@Composable
fun DetailsScreen(
    viewModel: ThumbnailViewModel, // Assume ViewModel can fetch details by ID
    navController: NavController
) {
    val thumbnail = viewModel.getSelectedThumbnail()
    val context = LocalContext.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .semantics {
                contentDescription = "Details screen for thumbnail ${thumbnail?.title ?: "loading"}"
            }
    ) {
        if (thumbnail != null) {
            val dimensions = extractImageDimensions(thumbnail.description)

            if (maxWidth > maxHeight) {
                // Landscape layout
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image Section
                    AsyncImage(
                        model = thumbnail.media.m,
                        placeholder = painterResource(id = R.drawable.images),
                        contentDescription = thumbnail.title,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .animateContentSize()
                    )
                    dimensions?.let { (width, height) ->
                        Text(
                            text = "Image Dimensions: ${width}x${height}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.animateContentSize()
                        )
                    }

                    // Details Section
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = thumbnail.title,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .animateContentSize()
                                .semantics {
                                    contentDescription = "Title: ${thumbnail.title}"
                                }
                        )

                        Text(
                            text = thumbnail.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .animateContentSize()
                                .semantics {
                                    contentDescription = "Description: ${thumbnail.description}"
                                }
                        )

                        Text(
                            text = "Author: ${thumbnail.link}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .animateContentSize()
                                .semantics {
                                    contentDescription = "Author: ${thumbnail.link}"
                                }
                        )

                        Text(
                            text = "Published: ${thumbnail.date_taken}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .animateContentSize()
                                .semantics {
                                    contentDescription = "Published: ${thumbnail.date_taken}"
                                }
                        )
                    }
                }
            } else {
                // Portrait layout
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Image Section
                    AsyncImage(
                        model = thumbnail.media.m,
                        placeholder = painterResource(id = R.drawable.images),
                        contentDescription = thumbnail.title,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .animateContentSize()
                    )

                    // Details Section
                    Text(
                        text = thumbnail.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .animateContentSize()
                            .semantics {
                                contentDescription = "Title: ${thumbnail.title}"
                            }
                    )

                    Text(
                        text = thumbnail.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .animateContentSize()
                            .semantics {
                                contentDescription = "Description: ${thumbnail.description}"
                            }
                    )

                    Text(
                        text = "Author: ${thumbnail.link}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .animateContentSize()
                            .semantics {
                                contentDescription = "Author: ${thumbnail.link}"
                            }
                    )

                    Text(
                        text = "Published: ${thumbnail.date_taken}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .animateContentSize()
                            .semantics {
                                contentDescription = "Published: ${thumbnail.date_taken}"
                            }
                    )

                    Button(
                        onClick = {
                            shareThumbnail(thumbnail, context)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Share")
                    }
                }
            }
        } else {
            // Loading State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading details...")
            }
        }
    }


}


fun shareThumbnail(thumbnail: Items, context: Context) {

    val shareText = """
        Title: ${thumbnail.title}
        Description: ${thumbnail.description}
        Published: ${thumbnail.date_taken}
        Link: ${thumbnail.link}
    """.trimIndent()

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        thumbnail.media.m.let { imageUrl ->
            putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUrl))
            type = "image/*"
        }
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share Thumbnail"))
}


fun extractImageDimensions(description: String): Pair<Int, Int>? {
    val widthRegex = """width="(\d+)"""".toRegex()
    val heightRegex = """height="(\d+)"""".toRegex()

    val width = widthRegex.find(description)?.groups?.get(1)?.value?.toIntOrNull()
    val height = heightRegex.find(description)?.groups?.get(1)?.value?.toIntOrNull()

    return if (width != null && height != null) {
        width to height
    } else {
        null
    }
}
