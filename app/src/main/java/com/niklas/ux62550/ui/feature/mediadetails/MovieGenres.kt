package com.niklas.ux62550.ui.feature.mediadetails

import DrawCircle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.ui.feature.common.ImageSize
import com.niklas.ux62550.ui.feature.common.MediaItem

@Composable
fun Genres(modifier: Modifier = Modifier, genres: MovieState.Data, providerState: ProviderState) {
    Row(
        modifier = modifier
            .padding(20.dp, 10.dp, 20.dp, 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for ((index, genre) in genres.mediaDetailObject.genre.withIndex().takeWhile { it.index <= 2 }) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.Gray)
                    .padding(8.dp, 4.dp)
                    .shadow(
                        elevation = 15.dp,
                        shape = RoundedCornerShape(40.dp),
                        ambientColor = Color.Black.copy(alpha = 255f), // Slightly less opaque for a softer effect
                        spotColor = Color.Black.copy(alpha = 255f)
                    )
            ) {
                Text(
                    text = genre.genreName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)                    ,

                )
            }
            if ((index != 2) && (index != genres.mediaDetailObject.genre.lastIndex)) {
                Spacer(modifier = Modifier.width(4.dp))
                DrawCircle(Modifier.size(10.dp).shadow(10.dp, RoundedCornerShape(40.dp), false,Color.Black), Color.LightGray, )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))
        when (providerState) {
            ProviderState.Empty -> {
                Text("NO PIC")
            }
            is ProviderState.Data -> {
                val country = providerState.providerDataObject["DK"]
                val StreamRentAndBuyProviderMap =
                    (country?.flatrate?.map { it.logoPath } ?: emptyList()) +
                        (country?.rent?.map { it.logoPath } ?: emptyList()) +
                        (country?.buy?.map { it.logoPath } ?: emptyList())

                if (country != null) {
                    for (i in 0 until minOf(3, StreamRentAndBuyProviderMap.size)) {
                        Spacer(modifier = Modifier.width(4.dp))
                        MediaItem(
                            StreamRentAndBuyProviderMap[i],
                            0.dp,
                            modifier
                                .clip(CircleShape)
                                .size(32.dp),
                            size = ImageSize.LOGO
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}