package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.models.Movie
import com.niklas.ux62550.ui.theme.DescriptionColor


@Composable
fun MovieDetailsAPI(items: List<MediaObject>) {
    // Find the media object for "The Office" or use a placeholder
    val MovieId = items.find { it.title == "The Office" }
    val title = MovieId?.title ?: "The Office Not Found"
    val relasedate = items.find { it.title == "The Office" }

    // Display the title
    if (MovieId != null) {
        MovieDetailItems(title = title, relasedate = MovieId.release_date)
    }
}

@Composable
fun MovieDetailItems(title: String, relasedate: String?) {
    val title = title.ifBlank { "Hello World" }
    val relasedate = relasedate?.ifBlank{"Not known"}
    TitleText(title)
}
@Composable
fun TitleText(movieTitle: String) {
    Text(
        text = movieTitle,
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 200.dp, 0.dp, 0.dp),
    )
}
@Composable
fun DescriptionText(movieDescription: String) {
    Text(
        text = movieDescription,
        style = TextStyle(
            lineHeight = 1.25.em,
            lineBreak = LineBreak.Paragraph,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            color = DescriptionColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 0.dp, 0.dp, 0.dp),
    )
}