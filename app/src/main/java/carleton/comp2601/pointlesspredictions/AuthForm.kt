package carleton.comp2601.pointlesspredictions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carleton.comp2601.pointlesspredictions.ui.theme.PointlessPredictionsTheme
import carleton.comp2601.pointlesspredictions.ui.theme.Shapes

@Composable
fun AuthForm(
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    username: String?,
    password: String?,
    enableAuthentication: Boolean,
    onUsernameChanged: (username: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
    onToggleMode: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(32.dp))
        AuthTitle(authMode = authMode)

        // USERNAME TEXTFIELD
        Spacer(modifier = Modifier.height(5.dp))
        UsernameInput(modifier = Modifier.fillMaxWidth(), username = username ?: "", onUsernameChanged = onUsernameChanged)

        // PASSWORD TEXTFIELD
        Spacer(modifier = Modifier.height(10.dp))
        PasswordInput(modifier = Modifier.fillMaxWidth(), password = password ?: "", onPasswordChanged = onPasswordChanged, onDoneClicked = onAuthenticate)

        // SIGN IN BUTTON
        Spacer(modifier = Modifier.height(10.dp))
        AuthButton(authMode = authMode, enableAuthentication = enableAuthentication, onAuthenticate = onAuthenticate)

        // SIMPLE DIVIDER ELEMENT
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 10.dp)
        )

        // SIGN UP ROW
        Spacer(modifier = Modifier.height(10.dp))
        ToggleAuthModeButton(modifier = Modifier.fillMaxWidth(), authMode = authMode, toggleAuth = { onToggleMode() })
    }
}

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

@Composable
fun AuthButton (
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    enableAuthentication: Boolean,
    onAuthenticate: () -> Unit
){
    Button(onClick = { onAuthenticate() }, enabled = enableAuthentication, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(
                if (authMode == AuthMode.SIGN_IN) {
                    R.string.action_sign_in
                } else {
                    R.string.action_sign_up
                }
            ),
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun ToggleAuthModeButton(
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    toggleAuth: () -> Unit
){
    Button(onClick = { toggleAuth() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(
                if (authMode == AuthMode.SIGN_IN) {
                    R.string.action_need_account
                } else {
                    R.string.action_already_have_account
                }
            ),
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    inValue: String?,
    onValueChange: (inValue: String) -> Unit,
) {
    // var value by remember { mutableStateOf("") }

    TextField(
        value = inValue ?: "",
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label, color = Color.Black) },
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
        keyboardActions = keyboardActions
    )
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
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
}

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
            TextButton(onClick = { dismissError() }) {
                Text(text = stringResource(id = R.string.error_action))
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.error_title),
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = error
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PointlessPredictionsTheme {
        Box(modifier = Modifier.padding(20.dp).background(color = Color.Black)){
            AuthForm(
                authMode = AuthMode.SIGN_IN,
                username = "",
                password = "",
                enableAuthentication = true,
                onUsernameChanged = {},
                onPasswordChanged = {},
                onAuthenticate = {},
                onToggleMode = {}
            )
        }
    }
}