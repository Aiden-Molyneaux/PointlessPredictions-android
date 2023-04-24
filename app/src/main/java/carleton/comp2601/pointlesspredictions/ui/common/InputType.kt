package carleton.comp2601.pointlesspredictions.ui.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

sealed class InputType(
    val label: String,
    val placeholderText: String = "",
    val icon: ImageVector? = null,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Username:InputType(
        label = "Username",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password:InputType(
        label = "Password",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )

    object PredictionText:InputType(
        label = "Prediction",
        placeholderText = "You predict that ...",
        icon = Icons.Default.AutoFixHigh,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        visualTransformation = VisualTransformation.None
    )

    object Day:InputType(
        label = "DD",
        placeholderText = "31",
        icon = Icons.Default.Today,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Month:InputType(
        label = "MM",
        placeholderText = "12",
        icon = Icons.Default.CalendarMonth,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Year:InputType(
        label = "YYYY",
        placeholderText = "2023",
        icon = Icons.Default.CalendarToday,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        visualTransformation = VisualTransformation.None
    )
}