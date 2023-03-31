package carleton.comp2601.pointlesspredictions

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.entities.User
import carleton.comp2601.pointlesspredictions.ui.theme.PointlessPredictionsTheme
import carleton.comp2601.pointlesspredictions.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AuthForm (
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    username: String?,
    password: String?,
    enableAuthentication: Boolean,
    navController: NavController,
    repo: UserRepository,
    dao: UserDao,
    onUsernameChanged: (username: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onAuthenticate: () -> Unit,
    onToggleMode: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val windowInfo = rememberWindowInfo()
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            Spacer(modifier = Modifier.height(32.dp))
        }

        AuthTitle(authMode = authMode)
        customDivider()
        // USERNAME TEXTFIELD
        UsernameInput(modifier = Modifier.fillMaxWidth(), username = username ?: "", onUsernameChanged = onUsernameChanged)

        // PASSWORD TEXTFIELD
        Spacer(modifier = Modifier.height(10.dp))
        PasswordInput(modifier = Modifier.fillMaxWidth(), password = password ?: "", onPasswordChanged = onPasswordChanged, onDoneClicked = onAuthenticate)

        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
            // SIGN IN BUTTON
            Spacer(modifier = Modifier.height(10.dp))
            AuthButton(navController = navController, modifier = Modifier.fillMaxWidth(), repo = repo, dao = dao, authMode = authMode, enableAuthentication = enableAuthentication, onAuthenticate = onAuthenticate)

            // SIMPLE DIVIDER ELEMENT
            customDivider()

            // SIGN UP ROW
            // Spacer(modifier = Modifier.height(10.dp))
            ToggleAuthModeButton(modifier = Modifier.fillMaxWidth(), authMode = authMode, toggleAuth = { onToggleMode() })
        } else {
            // SIMPLE DIVIDER ELEMENT
            customDivider()

            Row (modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // SIGN IN BUTTON
                    AuthButton(modifier = Modifier.fillMaxWidth(), navController = navController, repo = repo, dao = dao, authMode = authMode, enableAuthentication = enableAuthentication, onAuthenticate = onAuthenticate)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TOGGLE AUTH MODE BUTTON
                    ToggleAuthModeButton(modifier = Modifier.fillMaxWidth(), altText = true, authMode = authMode, toggleAuth = { onToggleMode() })
                }
            }
        }
    }
}

@Composable
fun customDivider() {
    Divider(
        color = Color.White.copy(alpha = 0.3f),
        thickness = 1.dp,
        modifier = Modifier.padding(top = 10.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))
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
    navController: NavController,
    repo: UserRepository,
    dao: UserDao,
    onAuthenticate: () -> Unit
){
    Button(
        onClick = {
            onAuthenticate()
        },
        enabled = enableAuthentication,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = modifier
    ) {
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
    altText: Boolean = false, // altText is only true when device is in non-compact mode
    toggleAuth: () -> Unit
){
    Button(onClick = { toggleAuth() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary), modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(
                if (authMode == AuthMode.SIGN_IN && altText) {
                    R.string.action_need_account_alt_text
                }
                else if (authMode == AuthMode.SIGN_IN) {
                    R.string.action_need_account
                }
                else if (authMode == AuthMode.SIGN_UP && altText) {
                    R.string.action_already_have_account_alt_text
                }
                else {
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
        keyboardActions = keyboardActions
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

enum class AuthMode {
    SIGN_UP, SIGN_IN
}

data class AuthState(
    val authMode: AuthMode = AuthMode.SIGN_IN,
    val username: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    fun isFormValid(): Boolean {
        return password?.isNotEmpty() == true && username?.isNotEmpty() == true // && authMode == AuthMode.SIGN_IN
    }
}

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