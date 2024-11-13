package com.niklas.ux62550.ui.screen_wachlist
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niklas.ux62550.R
import com.niklas.ux62550.navigation.ScreenWithGeneralNavBar
import com.niklas.ux62550.models.MovieBox
import com.niklas.ux62550.models.figmaPxToDp_h
import com.niklas.ux62550.models.figmaPxToDp_w
import com.niklas.ux62550.models.NonMovieBox
import com.niklas.ux62550.ui.screen_home.LogoBox
import com.niklas.ux62550.ui.screen_search.MovieBoxRow
import com.niklas.ux62550.ui.theme.SeachColorForText
import com.niklas.ux62550.ui.theme.UX62550Theme


@Composable
@Preview(showBackground = true, name = "SearchPreview")
fun WachlistPreview() {
    val movieBoxes = listOf(
        MovieBox("Name 1", R.drawable.logo, Color.Blue, "Movie", 3.5f),
        MovieBox("Name 2", R.drawable.logo, Color.Red, "Series", 4.5f)
    )

    UX62550Theme (darkTheme = true, dynamicColor = false) {
        ScreenWithGeneralNavBar {
            ScreenWachlist(movieBoxItemsUIState = MovieBoxItemsUIState.Data(movieBoxes))
        }
    }
}

fun setTrue(){

}

@Composable
fun ScreenWachlist(modifier: Modifier = Modifier, movieBoxItemsUIState: MovieBoxItemsUIState
) {
    Column(modifier.padding()) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(figmaPxToDp_w(0f), figmaPxToDp_h(40f), 0.dp, figmaPxToDp_h(17.5f)), // ConvertPxToDp(29.5f)
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoBox(size= figmaPxToDp_w(180f))
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(figmaPxToDp_w(0f), figmaPxToDp_h(20f), 0.dp), // ConvertPxToDp(29.5f)
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Watch List", fontSize = 36.sp)
                Button(
                    onClick = { /* Do something! */ },
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFACACAC)),
                    contentPadding = PaddingValues(horizontal = 5.dp, vertical = 0.dp),
                    modifier = modifier
                        .height(25.dp)
                        .width(90.dp)
                )
                { Text(
                    "Sort by"
                )
                    Spacer(modifier = Modifier.weight(0.1f))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Icon",
                    )
                }
            }

        }









        when (movieBoxItemsUIState) {
            MovieBoxItemsUIState.Empty -> {
                Text(
                    text = "No movies to be found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SeachColorForText
                )
            }
            is MovieBoxItemsUIState.Data -> {
                // Display list of movie items in LazyColumn
                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    items(movieBoxItemsUIState.movieBoxes) { movieBoxItem ->
                        HorizontalDivider(
                            color = SeachColorForText,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(0.7f)
                        )
                        MovieBoxRow(movieBox = movieBoxItem)
                    }
                }
            }
        }

    }
}
