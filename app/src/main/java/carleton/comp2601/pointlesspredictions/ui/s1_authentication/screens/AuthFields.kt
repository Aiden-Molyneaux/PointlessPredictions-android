package carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import carleton.comp2601.pointlesspredictions.ui.common.InputType
import carleton.comp2601.pointlesspredictions.ui.common.TextInput

val passwordFocusRequester = FocusRequester()

@Composable
fun UsernameInput(
    modifier: Modifier = Modifier,
    username: String?,
    onUsernameChanged: (username: String) -> Unit
) {
    TextInput(
        InputType.Username,
        inValue = username ?: "",
        onValueChange = { username ->
            onUsernameChanged(username)
        },
        keyboardActions = KeyboardActions(onNext = {
            passwordFocusRequester.requestFocus()
        })
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChanged: (password: String) -> Unit,
    onDoneClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextInput(
        InputType.Password,
        inValue = password ?: "",
        onValueChange = {
            onPasswordChanged(it)
        },
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onDoneClicked()
        }),
        focusRequester = passwordFocusRequester
    )
}