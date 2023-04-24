package carleton.comp2601.pointlesspredictions.ui.common

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    object HomeScreen : Screen("home_screen")
    object ProfileScreen : Screen("profile_screen")
    object FriendsScreen : Screen("friends_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}