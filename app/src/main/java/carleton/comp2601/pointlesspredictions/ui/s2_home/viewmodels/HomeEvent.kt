package carleton.comp2601.pointlesspredictions.ui.s2_home.viewmodels

import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao

sealed class HomeEvent {
    class inititalizeHomeScreen(val user_id: String?, val dao: UserDao):
            HomeEvent()

    class PredictionTextChanged(val predictionText: String):
            HomeEvent()

    class ExpirationCheckChanged(val isChecked: Boolean):
            HomeEvent()

    class DayChanged(val day: String):
            HomeEvent()

    class MonthChanged(val month: String):
            HomeEvent()

    class YearChanged(val year: String):
            HomeEvent()

    class PredictionSubmitted(val predictionText: String, val isChecked: Boolean, val day: String, val month: String, val year: String, val user_id: Int, val dao: UserDao):
            HomeEvent()

    class NavigateScreen(val navController: NavController, val newScreen: String):
            HomeEvent()
}