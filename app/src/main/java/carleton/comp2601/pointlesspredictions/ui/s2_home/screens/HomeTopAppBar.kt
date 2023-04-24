package carleton.comp2601.pointlesspredictions.ui.s2_home.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeTopAppBar() {
    TopAppBar(
        modifier = Modifier.height(50.dp),
        backgroundColor = MaterialTheme.colors.onSecondary,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Howdy, Pointless Predictor!",
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
    }
}