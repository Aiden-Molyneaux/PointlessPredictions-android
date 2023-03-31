package carleton.comp2601.pointlesspredictions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import carleton.comp2601.pointlesspredictions.entities.Prediction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    val uiState = MutableStateFlow(MainState())

    fun handleEvent(mainEvent: MainEvent) {
        when (mainEvent) {
            is MainEvent.inititalizeMainScreen -> {
                initializeMainScreen(mainEvent.user_id, mainEvent.dao)
            }
            is MainEvent.PredictionTextChanged -> {
                updatePredictionText(mainEvent.predictionText)
            }
            is MainEvent.ExpirationCheckChanged -> {
                updateIsChecked(mainEvent.isChecked)
            }
            is MainEvent.DayChanged -> {
                updateDay(mainEvent.day)
            }
            is MainEvent.MonthChanged -> {
                updateMonth(mainEvent.month)
            }
            is MainEvent.YearChanged -> {
                updateYear(mainEvent.year)
            }
            is MainEvent.PredictionSubmitted -> {
                placePrediction(mainEvent.predictionText, mainEvent.isChecked, mainEvent.day, mainEvent.month, mainEvent.year, mainEvent.user_id, mainEvent.dao)
            }
        }
    }

    // initializeMainScreen is called when MainScreen is composed, prepares state values
    private fun initializeMainScreen(user_id: String?, dao: UserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = dao.findUserById(user_id)
            val username = user.userName
            val intUserId = user.user_id

            val predictionList = dao.getAllPredictions()

            uiState.value = uiState.value.copy(
                username = username,
                user_id = intUserId,
                predictionList = predictionList
            )
        }
    }

    private fun updatePredictionText(predictionText: String?) {
        uiState.value = uiState.value.copy(
            predictionText = predictionText
        )
    }

    private fun updateIsChecked(isChecked: Boolean) {
        uiState.value = uiState.value.copy(
            isChecked = isChecked
        )
    }

    private fun updateDay(day: String?) {
        uiState.value = uiState.value.copy(
            day = day
        )
    }

    private fun updateMonth(month: String?) {
        uiState.value = uiState.value.copy(
            month = month
        )
    }

    private fun updateYear(year: String?) {
        uiState.value = uiState.value.copy(
            year = year
        )
    }

    private fun placePrediction(
        predictionText: String,
        isChecked: Boolean,
        day: String?,
        month: String?,
        year: String?,
        user_id: Int,
        dao: UserDao
    ){
        viewModelScope.launch(Dispatchers.Main) {
            // Format current date
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val current = LocalDate.now().format(formatter)

            // Find the ID of the last Prediction in the DB - allows us to know the ID of the next prediction
            // TO-DO: Make other DB function to only get the last prediction, rather than getting all
            val predictions = dao.getAllPredictions()
            var topID = 0
            predictions.forEach {
                topID = it.prediction_id
            }

            // Flow for entering a Prediction W/O expiration-date specified
            // TO-DO: Rename isChecked state variable to something more meaningful
            if(!isChecked) {
                val newPrediction = Prediction(topID+1, user_id, predictionText, current, "")
                dao.addPrediction(newPrediction)

                val predictionList = dao.getAllPredictions()
                uiState.value = uiState.value.copy(
                    predictionList = predictionList
                )

                // Prediction is made, time to exit function
                return@launch
            }

            // Flow for entering a Prediction WITH expiration-date specified
            val expiration_date = year + "-" + month + "-" + day // construct expiration-date

            val newPrediction = Prediction(topID+1, user_id, predictionText, current, expiration_date)
            dao.addPrediction(newPrediction)

            // Update predictionList so that the MainPredictionWindow (user feed) updates when new Prediction is placed
            val predictionList = dao.getAllPredictions()
            uiState.value = uiState.value.copy(
                predictionList = predictionList
            )
        }
    }
}

data class MainState (
    val user_id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    val predictionText: String? = null,
    val day: String? = null,
    val month: String? = null,
    val year: String? = null,
    val isChecked: Boolean = true,
    val error: String? = null,
    val predictionList: List<Prediction> = emptyList()
)