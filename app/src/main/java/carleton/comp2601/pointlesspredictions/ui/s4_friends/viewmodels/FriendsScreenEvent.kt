package carleton.comp2601.pointlesspredictions.ui.s4_friends.viewmodels

import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User

sealed class FriendsScreenEvent {
    class inititalizeFriendsScreen(val user_id: String?, val dao: UserDao):
        FriendsScreenEvent()

    class PredictionTextChanged(val predictionText: String):
        FriendsScreenEvent()

    class ExpirationCheckChanged(val isChecked: Boolean):
        FriendsScreenEvent()

    class DayChanged(val day: String):
        FriendsScreenEvent()

    class MonthChanged(val month: String):
        FriendsScreenEvent()

    class YearChanged(val year: String):
        FriendsScreenEvent()

    class PredictionSubmitted(val predictionText: String, val isChecked: Boolean, val day: String, val month: String, val year: String, val user_id: Int, val dao: UserDao):
        FriendsScreenEvent()

    class PredictionStatusConfirmed(val prediction: Prediction, val status: Boolean, val dao: UserDao):
        FriendsScreenEvent()

    class FriendStatusChanged(val user: User, val newStatus: String, val dao: UserDao):
        FriendsScreenEvent()

    class NavigateScreen(val navController: NavController, val newScreen: String):
        FriendsScreenEvent()

    class Logout(val navController: NavController):
        FriendsScreenEvent()
}