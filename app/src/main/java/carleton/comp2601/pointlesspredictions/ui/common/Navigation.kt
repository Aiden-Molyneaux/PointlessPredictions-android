package carleton.comp2601.pointlesspredictions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.data.UserDatabase
import carleton.comp2601.pointlesspredictions.data.UserRepository
import carleton.comp2601.pointlesspredictions.ui.common.Screen
import carleton.comp2601.pointlesspredictions.ui.s4_friends.screens.FriendsScreen

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    val dao : UserDao = UserDatabase.getInstance(context).userDao
    val repo : UserRepository = UserRepository(dao)

    NavHost(navController = navController, startDestination = Screen.AuthScreen.route) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(navController = navController, repo, dao)
        }
        composable(
            route = Screen.HomeScreen.route + "/{user_id}",
            arguments = listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) {entry ->
            HomeScreen(navController, repo, dao, user_id = entry.arguments?.getString("user_id"))
        }
        composable(
            route = Screen.ProfileScreen.route + "/{user_id}",
            arguments = listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) {entry ->
            ProfileScreen(navController, repo, dao, user_id = entry.arguments?.getString("user_id"))
        }
        composable(
            route = Screen.FriendsScreen.route + "/{user_id}",
            arguments = listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) {entry ->
            FriendsScreen(navController, repo, dao, user_id = entry.arguments?.getString("user_id"))
        }
    }
}
