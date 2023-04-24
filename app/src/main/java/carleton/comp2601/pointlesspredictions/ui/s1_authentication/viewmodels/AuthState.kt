package carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels

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

enum class AuthMode {
    SIGN_UP, SIGN_IN
}