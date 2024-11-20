import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorsAndDirectorsPopUp(){

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(4) {
            Spacer(modifier = Modifier.width(4.dp))
            DrawCircle(Modifier.size(64.dp), Color.Red)
        }
        Spacer(modifier = Modifier.width(2.dp))
        repeat(3) { // Create clickable circles
            Spacer(modifier = Modifier.width(2.dp))
            IconButton(
                onClick = {
                    showBottomSheet = true
                },
                modifier = Modifier.size(12.dp) // Size of the clickable button area
            ) {
                DrawCircle(Modifier.size(10.dp), Color.LightGray) // Circle inside the button
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    scrimColor = Color.Transparent,
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    for (i in 0..3) {
                        Row(
                            modifier = Modifier.padding(50.dp, 0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DrawCircle(Modifier.size(65.dp), color = Color.Red)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Some Actor Name",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                HorizontalDivider(
                                    modifier = Modifier.width(130.dp).padding(0.dp, 0.dp),
                                    thickness = 0.5.dp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().padding(110.dp, 0.dp),
                            thickness = 0.5.dp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}