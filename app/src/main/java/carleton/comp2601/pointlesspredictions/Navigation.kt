package carleton.comp2601.pointlesspredictions

import UserScreen
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    val dao : UserDao = UserDatabase.getInstance(context).userDao
    val repo : UserRepository = UserRepository(dao)

    NavHost(navController = navController, startDestination = Screen.MainScreen.route + "/{user_id}") {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, repo, dao)
        }
        composable(
            route = Screen.MainScreen.route + "/{user_id}",
            arguments = listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) {entry ->
            MainScreen(navController, repo, dao, user_id = entry.arguments?.getString("user_id"))
        }
        composable(
            route = Screen.UserScreen.route + "/{user_id}",
            arguments = listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) {entry ->
            UserScreen(navController, repo, dao, user_id = entry.arguments?.getString("user_id"))
        }
    }
}
