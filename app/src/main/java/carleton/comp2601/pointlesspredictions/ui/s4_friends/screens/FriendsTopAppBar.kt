package carleton.comp2601.pointlesspredictions.ui.s4_friends.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FriendsTopAppBar(
    onLogoutClicked: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.height(50.dp),
        backgroundColor = MaterialTheme.colors.onSecondary,
    ) {
        Box(){
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Your Friends",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colors.secondary,
                            offset = Offset(5.0f, 5.0f),
                            blurRadius = 10f
                        )
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                Button(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { onLogoutClicked() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Text(
                        text = "Logout",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}