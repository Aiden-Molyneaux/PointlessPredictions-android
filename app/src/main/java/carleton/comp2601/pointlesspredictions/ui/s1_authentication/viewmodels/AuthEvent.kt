package carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels

import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao

sealed class AuthEvent {
    object ToggleAuthMode: AuthEvent()

    class UsernameChanged(val username: String):
        AuthEvent()

    class PasswordChanged(val password: String):
        AuthEvent()

    class Authenticate(val navController: NavController, val dao : UserDao, val username: String, val password: String):
        AuthEvent()

    object ErrorDismissed: AuthEvent()
}