package carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun AuthAboutUsButton() {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Row(){
                    Text(
                        text = "About ",
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = "'Pointless Predictions'",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                }

            },
            text = {
                Column(){
                    Text(
                        text = "'Pointless Predictions' is being developed by Aiden Molyneaux, a student at Carleton University. The application is a term-project.",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = " "
                    )
                    Text(
                        text = "User's make pointless predictions to share with friends!",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(
                        text = "Got it!",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.background
        )
    }

    TextButton(onClick = { showDialog = true }) {
        val dialogText = buildAnnotatedString {
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("About Pointless Predictions")
            }
        }

        Text(
            text = dialogText,
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}