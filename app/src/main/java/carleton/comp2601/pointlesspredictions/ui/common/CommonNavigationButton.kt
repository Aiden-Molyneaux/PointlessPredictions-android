package carleton.comp2601.pointlesspredictions.ui.common

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun CommonNavigationButton(
    modifier: Modifier,
    label: String,
    navigateTo: () -> Unit
) {
    Button(
        modifier = modifier.wrapContentHeight(),
        onClick = { navigateTo() },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}