package carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import carleton.comp2601.pointlesspredictions.R
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels.AuthMode

@Composable
fun AuthTitle(
    modifier: Modifier = Modifier,
    authMode: AuthMode
){
    Text(
        text = stringResource (
            if (authMode == AuthMode.SIGN_IN) {
                R.string.label_sign_in_to_account
            } else {
                R.string.label_sign_up_for_account
            }
        ),
        color = MaterialTheme.colors.onBackground,
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center,
        style = TextStyle(
            shadow = Shadow(
                color = MaterialTheme.colors.secondary,
                offset = Offset(5.0f, 10.0f),
                blurRadius = 10f
            )
        ),
    )
}