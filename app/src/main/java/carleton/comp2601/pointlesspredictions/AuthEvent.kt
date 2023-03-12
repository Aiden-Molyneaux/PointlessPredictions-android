package carleton.comp2601.pointlesspredictions

import androidx.lifecycle.LiveData

sealed class AuthEvent {
    object ToggleAuthMode: AuthEvent()

    class UsernameChanged(val username: String):
        AuthEvent()

    class PasswordChanged(val password: String):
        AuthEvent()

    class Authenticate(val homeViewModel: HomeViewModel, val users: LiveData<List<User>>, val username: String, val password: String):
        AuthEvent()

    object ErrorDismissed: AuthEvent()
}