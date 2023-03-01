package carleton.comp2601.pointlesspredictions

sealed class AuthEvent {
    object ToggleAuthMode: AuthEvent()

    class UsernameChanged(val username: String):
        AuthEvent()

    class PasswordChanged(val password: String):
        AuthEvent()

    class Authenticate(val username: String, val password: String):
        AuthEvent()

    object ErrorDismissed: AuthEvent()
}