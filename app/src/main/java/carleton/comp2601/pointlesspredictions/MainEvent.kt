package carleton.comp2601.pointlesspredictions

import androidx.compose.material.BottomDrawerState
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi

sealed class MainEvent {
    class inititalizeMainScreen(val user_id: String?, val dao: UserDao):
            MainEvent()

    class PredictionTextChanged(val predictionText: String):
            MainEvent()

    class ExpirationCheckChanged(val isChecked: Boolean):
            MainEvent()

    class DayChanged(val day: String):
            MainEvent()

    class MonthChanged(val month: String):
            MainEvent()

    class YearChanged(val year: String):
            MainEvent()

    class PredictionSubmitted(val predictionText: String, val isChecked: Boolean, val day: String, val month: String, val year: String, val user_id: Int, val dao: UserDao):
            MainEvent()
}