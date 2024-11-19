package com.niklas.ux62550.ui.feature.mediadetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.niklas.ux62550.data.model.MediaObject
import com.niklas.ux62550.data.remote.RemoteMediaDataSource.Companion.BASE_MOVIE_URL
import com.niklas.ux62550.ui.theme.DescriptionColor


@Composable
fun MovieDetailsAPI(items: List<MediaObject>) {
    val pagerState = rememberPagerState(pageCount = { items.size }, initialPage = items.size/2)
    val w = 350f
    val h = w/16*9
    HorizontalPager(state = pagerState,
        contentPadding = PaddingValues(start = Dp((LocalConfiguration.current.screenWidthDp - w) / 2)),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically)
    { page ->
        Card(
            Modifier
                .size(Dp(w), Dp(h))
        )  {
            MovieDetailItems(
                uri = items[page].backdrop_path,
                Title = items[page].title,
                width = Dp(w),
                height = Dp(h),
            )
        }
    }
}
@Composable
fun MovieDetailItems(uri: String?, Title: String,  width: Dp, height: Dp, round: Dp = 0.dp, modifier: Modifier = Modifier) {
    val imguri = if (uri!=null) BASE_MOVIE_URL + uri else "https://cataas.com/cat"
    val title = Title.ifBlank { "Hello World" }
    AsyncImage(
        model = imguri,
        contentDescription = null, // TODO: include content description
        modifier = modifier
            .clip(RoundedCornerShape(round))
            .size(width, height)
    )
    Text(
        text = title,
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(
                color = Color.Black, blurRadius = 10f
            ),
            textAlign = TextAlign.Center,
        )
    )
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