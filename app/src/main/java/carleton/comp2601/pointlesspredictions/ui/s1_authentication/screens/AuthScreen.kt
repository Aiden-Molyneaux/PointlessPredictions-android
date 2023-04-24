package carleton.comp2601.pointlesspredictions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.data.UserRepository
import carleton.comp2601.pointlesspredictions.ui.common.WindowInfo
import carleton.comp2601.pointlesspredictions.ui.common.rememberWindowInfo
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens.AuthErrorDialog
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels.AuthEvent
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels.AuthViewModel

@Composable
fun AuthScreen(navController: NavController, repo: UserRepository, dao : UserDao) {
    val windowInfo = rememberWindowInfo()
    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        Column (
            Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(48.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP ICON
            Icon(
                painter = painterResource(id = R.drawable.moon_icon2),
                contentDescription = null,
                Modifier.size(100.dp),
                tint = MaterialTheme.colors.onBackground
            )

            // APP NAME TEXT
            Text(
                text = "Pointless Predictions",
                color = MaterialTheme.colors.onBackground,
                fontSize = 64.sp,
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
                modifier = Modifier.padding(top = 40.dp)
            )

            LoginForm(navController, repo, dao)
        }
    } else {
        Row (
            Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TOP ICON
                Icon(
                    painter = painterResource(id = R.drawable.moon_icon2),
                    contentDescription = null,
                    Modifier.size(100.dp),
                    tint = MaterialTheme.colors.onBackground
                )

                // APP NAME TEXT
                Text(
                    text = "Pointless Predictions",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 48.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colors.secondary,
                            offset = Offset(5.0f, 10.0f),
                            blurRadius = 10f
                        )
                    )
                    // modifier = Modifier.padding(top = 0.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LoginForm(navController, repo, dao)
            }

        }
    }
}

@Composable
fun LoginForm(
    navController: NavController,
    repo: UserRepository,
    dao: UserDao
) {
    val viewModel: AuthViewModel = viewModel()
    val authState = viewModel.uiState.collectAsState().value
    val handleEvent = viewModel::handleEvent

    MaterialTheme {
        Column (
            Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (authState.isLoading) {
                CircularProgressIndicator()
            } else {
                AuthForm(
                    modifier = Modifier.fillMaxSize(),
                    authMode = authState.authMode,
                    username = authState.username ?: "",
                    password = authState.password ?: "",
                    enableAuthentication = authState.isFormValid(),
                    navController = navController,
                    repo = repo,
                    dao = dao,
                    onUsernameChanged = {
                        handleEvent(AuthEvent.UsernameChanged(it))
                    },
                    onPasswordChanged = {
                        handleEvent(AuthEvent.PasswordChanged(it))
                    },
                    onAuthenticate = {
                        handleEvent(AuthEvent.Authenticate(navController, dao,authState.username ?: "", authState.password ?: ""))
                    },
                    onToggleMode = {
                        handleEvent(AuthEvent.ToggleAuthMode)
                    }
                )
                authState.error?.let { error ->
                    AuthErrorDialog(
                        error = error,
                        dismissError = {
                            handleEvent(AuthEvent.ErrorDismissed)
                        }
                    )
                }
            }
        }
    }
}