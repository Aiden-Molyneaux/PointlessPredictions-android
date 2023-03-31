package carleton.comp2601.pointlesspredictions

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.entities.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel : ViewModel() {
    val uiState = MutableStateFlow(AuthState())

    fun handleEvent(authEvent: AuthEvent) {
        when (authEvent) {
            is AuthEvent.ToggleAuthMode -> {
                Log.d("EVENT", "ToggleAuthMode")
                toggleAuthMode()
            }
            is AuthEvent.UsernameChanged -> {
                updateUsername(authEvent.username)
            }
            is AuthEvent.PasswordChanged -> {
                updatePassword(authEvent.password)
            }
            is AuthEvent.Authenticate -> {
                authenticate(authEvent.navController, authEvent.dao, authEvent.username, authEvent.password)
            }
            is AuthEvent.ErrorDismissed -> {
                dismissError()
            }
        }
    }

    private fun toggleAuthMode() {
        val newAuthMode = if (uiState.value.authMode==AuthMode.SIGN_IN) AuthMode.SIGN_UP else AuthMode.SIGN_IN

        uiState.value = uiState.value.copy(
            authMode = newAuthMode
        )

        Log.d("NEW_AUTH", uiState.value.authMode.name)
    }

    private fun updateUsername(username: String) {
        uiState.value = uiState.value.copy(
            username = username
        )
    }

    private fun updatePassword(password: String) {
        uiState.value = uiState.value.copy(
            password = password
        )
    }

    private fun authenticate(
        navController: NavController,
        dao: UserDao,
        username: String,
        password: String
    ) {
        uiState.value = uiState.value.copy(
            isLoading = true
        )

        viewModelScope.launch(Dispatchers.Main) {
            if (uiState.value.authMode == AuthMode.SIGN_IN) {
                var isAuthenticated = false
                var currentUser : User? = null

                val users = dao.getAllUsers()
                users.forEach {
                    if (it.userName == username && it.password == password) {
                        isAuthenticated = true
                        currentUser = it
                    }
                }

                if (isAuthenticated) {
                    // IMPLEMENT PAGE SWITCH
                    Log.d("Logged in", "as " + currentUser?.userName)
                    navController.navigate(Screen.MainScreen.withArgs(currentUser!!.user_id.toString()))
                } else {
                    displayError("User with these credentials does not exist")
                }
            } else {
                var users = dao.getAllUsers()
                var topID = 0
                users.forEach {
                    topID = it.user_id
                    if (it.userName == username && it.password == password) {
                        displayError("User already exists!")
                        return@launch
                    }
                }
                var newUser = User(topID+1, username, password)
                dao.addUser(newUser)
                navController.navigate(Screen.MainScreen.withArgs(newUser.user_id.toString()))
            }
        }
    }

    private fun dismissError() {
        uiState.value = uiState.value.copy(
            error = null
        )
    }

    private suspend fun displayError(errorMsg: String) {
        withContext(Dispatchers.Main) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = errorMsg
            )
        }
    }
}

