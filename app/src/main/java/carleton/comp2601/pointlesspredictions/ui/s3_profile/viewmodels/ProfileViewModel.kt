package carleton.comp2601.pointlesspredictions.ui.s3_profile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.relations.UserWithPredictions
import carleton.comp2601.pointlesspredictions.ui.common.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProfileViewModel : ViewModel() {
    val uiState = MutableStateFlow(ProfileState())

    fun handleEvent(profileEvent: ProfileEvent) {
        when (profileEvent) {
            is ProfileEvent.inititalizeProfileScreen -> {
                initializeProfileScreen(profileEvent.user_id, profileEvent.dao)
            }
            is ProfileEvent.PredictionTextChanged -> {
                updatePredictionText(profileEvent.predictionText)
            }
            is ProfileEvent.ExpirationCheckChanged -> {
                updateIsChecked(profileEvent.isChecked)
            }
            is ProfileEvent.DayChanged -> {
                updateDay(profileEvent.day)
            }
            is ProfileEvent.MonthChanged -> {
                updateMonth(profileEvent.month)
            }
            is ProfileEvent.YearChanged -> {
                updateYear(profileEvent.year)
            }
            is ProfileEvent.PredictionSubmitted -> {
                placePrediction(profileEvent.predictionText, profileEvent.isChecked, profileEvent.day, profileEvent.month, profileEvent.year, profileEvent.user_id, profileEvent.dao)
            }
            is ProfileEvent.PredictionStatusConfirmed -> {
                updatePredictionStatus(profileEvent.prediction, profileEvent.status, profileEvent.dao)
            }
            is ProfileEvent.NavigateScreen -> {
                navigateToScreen(profileEvent.navController, profileEvent.newScreen)
            }
            is ProfileEvent.Logout -> {
                logout(profileEvent.navController)
            }
        }
    }

    // initializeProfileScreen is called when ProfileScreen is composed, prepares state values
    private fun initializeProfileScreen(user_id: String?, dao: UserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = dao.findUserById(user_id)
            val username = user.userName
            val intUserId = user.user_id

            val currentUser = dao.getUserWithPredictions(user_id = user_id!!.toInt())
            initializeCounters(currentUser.predictions)

            uiState.value = uiState.value.copy(
                username = username,
                user_id = intUserId,
                predictions = currentUser.predictions
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
            if (newScreen == "Home") {
                navController.navigate(Screen.HomeScreen.withArgs(uiState.value.user_id.toString()))
            } else {
                navController.navigate(Screen.FriendsScreen.withArgs(uiState.value.user_id.toString()))
            }
        }
    }

    private fun logout(navController: NavController) {
        viewModelScope.launch(Dispatchers.Main) {
            navController.navigate(Screen.AuthScreen.withArgs())
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

                val currentUser = dao.getUserWithPredictions(user_id = user_id)
                uiState.value = uiState.value.copy(
                    predictions = currentUser.predictions,
                    predictionText = "",
                    day = "",
                    month = "",
                    year = "",
                    isChecked = true
                )

                // Prediction is made, time to exit function
                return@launch
            }

            // Flow for entering a Prediction WITH expiration-date specified
            val expiration_date = year + "-" + month + "-" + day // construct expiration-date

            val newPrediction = Prediction(topID+1, user_id, predictionText, current, expiration_date)
            dao.upsertPrediction(newPrediction)

            // Update predictionList so that the MainPredictionWindow (user feed) updates when new Prediction is placed
            val currentUser : UserWithPredictions = dao.getUserWithPredictions(user_id = user_id)

            uiState.value = uiState.value.copy(
                predictions = currentUser.predictions,
                predictionText = "",
                day = "",
                month = "",
                year = "",
                isChecked = true
            )
        }
    }

    fun updatePredictionStatus(
        prediction: Prediction,
        status: Boolean,
        dao: UserDao
    ){
        viewModelScope.launch(Dispatchers.Main) {
            val newPrediction = Prediction(prediction.prediction_id, prediction.user_id, prediction.premise, prediction.datePlaced, prediction.expirationDate, status)
            dao.upsertPrediction(newPrediction)

            // Update predictionList so that the MainPredictionWindow (user feed) updates when new Prediction is placed
            val currentUser : UserWithPredictions = dao.getUserWithPredictions(user_id = prediction.user_id)

            uiState.value = uiState.value.copy(
                predictions = currentUser.predictions
            )
        }
    }
}