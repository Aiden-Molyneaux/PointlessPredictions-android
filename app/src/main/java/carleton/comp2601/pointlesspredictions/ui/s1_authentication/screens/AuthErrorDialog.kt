package carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import carleton.comp2601.pointlesspredictions.R

@Composable
fun AuthErrorDialog(
    modifier: Modifier = Modifier,
    error: String,
    dismissError: () -> Unit
){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            dismissError()
        },
        confirmButton = {
            Button(onClick = { dismissError() }) {
                Text(
                    text = stringResource(id = R.string.error_action),
                    color = Color.Black
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.error_title),
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground
            )
        },
        text = {
            Text(
                text = error,
                color = Color.Black
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )
}