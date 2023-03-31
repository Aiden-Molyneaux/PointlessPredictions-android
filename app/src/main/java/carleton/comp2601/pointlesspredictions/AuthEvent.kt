package carleton.comp2601.pointlesspredictions

import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.entities.User

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