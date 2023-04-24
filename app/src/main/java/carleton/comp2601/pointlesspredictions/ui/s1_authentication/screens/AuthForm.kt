package carleton.comp2601.pointlesspredictions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.ui.common.WindowInfo
import carleton.comp2601.pointlesspredictions.ui.common.rememberWindowInfo
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens.*
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels.AuthMode

@Composable
fun AuthForm (
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    username: String?,
    password: String?,
    enableAuthentication: Boolean,
    navController: NavController,
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
            TriggerAuthButton(navController = navController, modifier = Modifier.fillMaxWidth(), dao = dao, authMode = authMode, enableAuthentication = enableAuthentication, onAuthenticate = onAuthenticate)

            // SIMPLE DIVIDER ELEMENT
            customDivider()

            // SIGN UP ROW
            ToggleAuthModeButton(modifier = Modifier.fillMaxWidth(), authMode = authMode, toggleAuth = { onToggleMode() })

            // ABOUT POINTLESS PREDICTIONS
            AuthAboutUsButton()
        } else {
            // SIMPLE DIVIDER ELEMENT
            customDivider()

            Row (modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // SIGN IN BUTTON
                    TriggerAuthButton(modifier = Modifier.fillMaxWidth(), navController = navController, dao = dao, authMode = authMode, enableAuthentication = enableAuthentication, onAuthenticate = onAuthenticate)
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