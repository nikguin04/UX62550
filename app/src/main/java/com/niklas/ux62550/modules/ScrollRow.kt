package com.niklas.ux62550.modules

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.niklas.ux62550.figmaPxToDp_h
import com.niklas.ux62550.figmaPxToDp_w
import com.niklas.ux62550.PromoItem
import com.niklas.ux62550.dpToPx
import com.niklas.ux62550.models.MediaItem
import kotlin.math.roundToInt

@Composable
fun HorizontalLazyRowWithSnapEffect(items: List<MediaItem>) {
    val itemWidth = figmaPxToDp_w(300f) + figmaPxToDp_w(10f) *2 // width + padding*"
    val halfScreenWidth = LocalConfiguration.current.screenWidthDp.dp / 2
    val halfItemWidth = itemWidth / 2
    val offsetToCenter = halfScreenWidth - halfItemWidth
    val pixelOffset: Int = offsetToCenter.dpToPx().roundToInt()
    val listState = rememberLazyListState(1, -pixelOffset) // To manage the scroll state


    // List of items to display in the LazyRow
    val itemsList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    // LazyRow with snapping effect
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState), // Snap to item effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
    ) {

        items.forEachIndexed { index, mediaItem ->
            item {
                PromoItem(
                    width= figmaPxToDp_w(300f),
                    height= figmaPxToDp_h(200f),
                    round= figmaPxToDp_w(20f),
                    color= mediaItem.tempColor,
                    modifier = Modifier.padding(figmaPxToDp_w(10f) )
                )
            }
        }
        /*item { PromoItem(width= FigmaPxToDp_w(300f), height= FigmaPxToDp_h(200f), round= FigmaPxToDp_w(20f), color= Color(0xFF000022), modifier = Modifier.padding(
            FigmaPxToDp_w(10f) ) ) }
        item { PromoItem(width= FigmaPxToDp_w(300f), height= FigmaPxToDp_h(200f), round= FigmaPxToDp_w(20f), color= Color(0xFF006600), modifier = Modifier.padding(
            FigmaPxToDp_w(10f) ) ) }
        item { PromoItem(width= FigmaPxToDp_w(300f), height= FigmaPxToDp_h(200f), round= FigmaPxToDp_w(20f), color= Color(0xFF990000), modifier = Modifier.padding(
            FigmaPxToDp_w(10f) ) ) }*/
    }

}
