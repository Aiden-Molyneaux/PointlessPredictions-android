package carleton.comp2601.pointlesspredictions

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

class AuthViewModel : ViewModel() {
    //var HashMap<String, String> HashMap = new HashMap<String, String>()

    //val selection=HomeViewModel(UserRepository(UserDao))
    //val HomeViewModel: HomeViewModel = viewModel()

    val meMap = mutableStateMapOf<String, String>()


    val uiState = MutableStateFlow(AuthState())
    val authMode = uiState.value.authMode

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
                authenticate(authEvent.homeViewModel, authEvent.users, authEvent.username, authEvent.password)
            }
            is AuthEvent.ErrorDismissed -> {
                dismissError()
            }
        }
    }

    private fun toggleAuthMode() {
        Log.d("PREV_AUTH", authMode.name)
        val newAuthMode = if (authMode == AuthMode.SIGN_IN) {
            AuthMode.SIGN_UP
        } else {
            AuthMode.SIGN_IN
        }
        Log.d("NEW_AUTH1", newAuthMode.name)
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

    //@Composable
    private fun authenticate(homeViewModel: HomeViewModel, users: LiveData<List<User>>, username: String, password: String) {
        uiState.value = uiState.value.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)

//            if (authMode == AuthMode.SIGN_IN && meMap[username] == password) {
//                Log.d("SIGN_IN", "Does this work?")
//            } else if (authMode == AuthMode.SIGN_UP) {
//                meMap[username] = password
//                Log.d("SIGN_UP", "Does this work?")
//            } else {
//                Log.d("FAIL", "Does this work?")
//                Log.d("authMode", authMode.name)
//            }
            val user = User(
                id = 1,
                userName = uiState.value.username?: "",
                password = uiState.value.password?: ""
            )
            //addUserInDB(user, HomeViewModel)

            addUserInDB(user, homeViewModel)

            withContext(Dispatchers.Main) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Something went wrong!"
                )
            }
        }
    }

    private fun dismissError() {
        uiState.value = uiState.value.copy(
            error = null
        )
    }
}

