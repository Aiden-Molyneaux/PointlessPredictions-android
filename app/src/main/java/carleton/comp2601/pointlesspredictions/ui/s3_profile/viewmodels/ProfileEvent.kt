package carleton.comp2601.pointlesspredictions.ui.s3_profile.viewmodels

import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.entities.Prediction

sealed class ProfileEvent {
    class inititalizeProfileScreen(val user_id: String?, val dao: UserDao):
        ProfileEvent()

    class PredictionTextChanged(val predictionText: String):
        ProfileEvent()

    class ExpirationCheckChanged(val isChecked: Boolean):
        ProfileEvent()

    class DayChanged(val day: String):
        ProfileEvent()

    class MonthChanged(val month: String):
        ProfileEvent()

    class YearChanged(val year: String):
        ProfileEvent()

    class PredictionSubmitted(val predictionText: String, val isChecked: Boolean, val day: String, val month: String, val year: String, val user_id: Int, val dao: UserDao):
        ProfileEvent()

    class PredictionStatusConfirmed(val prediction: Prediction, val status: Boolean, val dao: UserDao):
        ProfileEvent()

    class NavigateScreen(val navController: NavController, val newScreen: String):
        ProfileEvent()

    class Logout(val navController: NavController):
        ProfileEvent()
}