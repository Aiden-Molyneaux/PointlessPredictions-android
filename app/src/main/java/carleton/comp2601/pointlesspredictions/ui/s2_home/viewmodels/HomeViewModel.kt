package carleton.comp2601.pointlesspredictions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.ui.common.Screen
import carleton.comp2601.pointlesspredictions.ui.s2_home.viewmodels.HomeEvent
import carleton.comp2601.pointlesspredictions.ui.s2_home.viewmodels.HomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {
    val uiState = MutableStateFlow(HomeState())

    fun handleEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.inititalizeHomeScreen -> {
                initializeHomeScreen(homeEvent.user_id, homeEvent.dao)
            }
            is HomeEvent.PredictionTextChanged -> {
                updatePredictionText(homeEvent.predictionText)
            }
            is HomeEvent.ExpirationCheckChanged -> {
                updateIsChecked(homeEvent.isChecked)
            }
            is HomeEvent.DayChanged -> {
                updateDay(homeEvent.day)
            }
            is HomeEvent.MonthChanged -> {
                updateMonth(homeEvent.month)
            }
            is HomeEvent.YearChanged -> {
                updateYear(homeEvent.year)
            }
            is HomeEvent.PredictionSubmitted -> {
                placePrediction(homeEvent.predictionText, homeEvent.isChecked, homeEvent.day, homeEvent.month, homeEvent.year, homeEvent.user_id, homeEvent.dao)
            }
            is HomeEvent.NavigateScreen -> {
                navigateToScreen(homeEvent.navController, homeEvent.newScreen)
            }
        }
    }

    // initializeHomeScreen is called when MainScreen is composed, prepares state values
    private fun initializeHomeScreen(user_id: String?, dao: UserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = dao.findUserById(user_id)
            val username = user.userName
            val intUserId = user.user_id

            val predictions = dao.getAllPredictions()
            initializeCounters(predictions)

            uiState.value = uiState.value.copy(
                username = username,
                user_id = intUserId,
                predictionList = predictions
            )
        }
    }

    private fun initializeCounters(predictions: List<Prediction>) {
        var numberOfTrue = 0
        var numberOfFalse = 0
        var numberOfUnconfirmed = 0
        predictions.forEach{
            if (it.confirmationStatus == true) {
                numberOfTrue += 1
            }
            else if (it.confirmationStatus == false) {
                numberOfFalse += 1
            }
            else {
                numberOfUnconfirmed += 1
            }
        }

        uiState.value = uiState.value.copy(
            numberOfTrue = numberOfTrue,
            numberOfFalse = numberOfFalse,
            numberOfUnconfirmed = numberOfUnconfirmed
        )
    }

    private fun navigateToScreen(navController: NavController, newScreen: String) {
        viewModelScope.launch(Dispatchers.Main) {
            if (newScreen == "Profile") {
                navController.navigate(Screen.ProfileScreen.withArgs(uiState.value.user_id.toString()))
            } else {
                navController.navigate(Screen.FriendsScreen.withArgs(uiState.value.user_id.toString()))
            }
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
                dao.upsertPrediction(newPrediction)

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
            dao.upsertPrediction(newPrediction)

            // Update predictionList so that the MainPredictionWindow (user feed) updates when new Prediction is placed
            val predictionList = dao.getAllPredictions()
            uiState.value = uiState.value.copy(
                predictionList = predictionList
            )
        }
    }
}