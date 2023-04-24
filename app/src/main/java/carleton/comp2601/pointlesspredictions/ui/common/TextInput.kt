package carleton.comp2601.pointlesspredictions.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import carleton.comp2601.pointlesspredictions.ui.theme.Shapes

@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    inValue: String?,
    onValueChange: (inValue: String) -> Unit,
) {
    TextField(
        value = inValue ?: "",
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { inputType.icon?.let { Icon(imageVector = it, null) } },
        label = { Text(text = inputType.label, color = Color.Black) },
        placeholder = { Text(text=inputType.placeholderText)},
        shape = Shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = MaterialTheme.colors.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions,
    )
}