package carleton.comp2601.pointlesspredictions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginForm() {
    val viewModel: AuthViewModel = viewModel()
    MaterialTheme {
        LoginContent(
              modifier = Modifier.fillMaxWidth(),
              authState = viewModel.uiState.collectAsState().value,
              handleEvent = viewModel::handleEvent
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    authState: AuthState,
    handleEvent: (event: AuthEvent) -> Unit
) {
    Column (
        Modifier
            .padding(24.dp)
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
                onUsernameChanged = {
                    handleEvent(AuthEvent.UsernameChanged(it))
                },
                onPasswordChanged = {
                    handleEvent(AuthEvent.PasswordChanged(it))
                },
                onAuthenticate = {
                    handleEvent(AuthEvent.Authenticate(authState.username ?: "", authState.password ?: ""))
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