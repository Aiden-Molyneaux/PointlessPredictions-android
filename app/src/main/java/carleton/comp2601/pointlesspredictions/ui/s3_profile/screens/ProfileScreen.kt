package carleton.comp2601.pointlesspredictions

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.data.UserRepository
import carleton.comp2601.pointlesspredictions.ui.common.CommonBottomAppBar
import carleton.comp2601.pointlesspredictions.ui.common.CommonNavigationButton
import carleton.comp2601.pointlesspredictions.ui.common.CommonPredictionCounter
import carleton.comp2601.pointlesspredictions.ui.common.prediction_pop_up.PredictionPopUpContent
import carleton.comp2601.pointlesspredictions.ui.s3_profile.screens.ProfileFeedWindow
import carleton.comp2601.pointlesspredictions.ui.s3_profile.screens.ProfileTopAppBar
import carleton.comp2601.pointlesspredictions.ui.s3_profile.viewmodels.ProfileEvent
import carleton.comp2601.pointlesspredictions.ui.s3_profile.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
/// RENDERED AFTER SUCCESSFUL LOGIN
fun ProfileScreen(navController: NavController, repo: UserRepository, dao: UserDao, user_id: String?) {
    val viewModel: ProfileViewModel = viewModel()
    val handleEvent = viewModel::handleEvent

    // on MainScreen initialization we need to access the database to fetch the user corresponding to the id passed to MainScreen
    handleEvent(ProfileEvent.inititalizeProfileScreen(user_id, dao))
    val profileState = viewModel.uiState.collectAsState().value

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    // DRAWER FOR PREDICTION POPUP
    BottomDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            PredictionPopUpContent(
                username = profileState.username,
                predictionText = profileState.predictionText,
                day = profileState.day,
                month = profileState.month,
                year = profileState.year,
                isChecked = profileState.isChecked,
                drawerState = drawerState,
                onPredictionTextChanged = {
                    handleEvent(ProfileEvent.PredictionTextChanged(it))
                },
                onExpirationCheckedChanged = {
                    handleEvent(ProfileEvent.ExpirationCheckChanged(it))
                },
                onDayChanged = {
                    handleEvent(ProfileEvent.DayChanged(it))
                },
                onMonthChanged = {
                    handleEvent(ProfileEvent.MonthChanged(it))
                },
                onYearChanged = {
                    handleEvent(ProfileEvent.YearChanged(it))
                },
                onPredictionSubmitted = {
                    handleEvent(ProfileEvent.PredictionSubmitted(predictionText = profileState.predictionText?:"", isChecked = profileState.isChecked, day = profileState.day?:"", month = profileState.month?:"", year = profileState.year?:"", user_id = profileState.user_id?:0, dao = dao))
                    scope.launch { drawerState.close() }
                }
            )
        }
    ){
        // MAIN PAGE CONTENT
        Scaffold(
            topBar = {
                ProfileTopAppBar(
                    onLogoutClicked = {
                        handleEvent(ProfileEvent.Logout(navController))
                    }
                )
             },
            content = {
                Column(modifier = Modifier
                    .fillMaxHeight()
                ){
                    CommonPredictionCounter(
                        numberOfTrue = profileState.numberOfTrue!!,
                        numberOfFalse = profileState.numberOfFalse!!,
                        numberOfUnconfirmed = profileState.numberOfUnconfirmed!!
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 8.dp, start = 20.dp, end = 20.dp, bottom = 92.dp)
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colors.onSecondary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(
                                color = MaterialTheme.colors.onBackground,
                                shape = RoundedCornerShape(10.dp)
                            ),
                    ) {
                        ProfileFeedWindow(
                            profileState.predictions,
                            onPredictionStatusConfirmed = { prediction, status ->
                                handleEvent(ProfileEvent.PredictionStatusConfirmed(prediction, status, dao))
                            }
                        )
                    }
                }
            },

            floatingActionButton = {
                Row(
                    modifier = Modifier
                        .offset(y = 6.dp)
                        .height(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                     CommonNavigationButton(
                         modifier = Modifier
                             .weight(1f)
                             .padding(start = 4.dp)
                             .fillMaxHeight(),
                         label = "Home",
                         navigateTo = {
                            handleEvent(ProfileEvent.NavigateScreen(navController, "Home"))
                        }
                     )

                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .weight(1.5f)
                            .fillMaxHeight(),
                        text = { Text("Make a Prediction", fontSize = 16.sp, textAlign = TextAlign.Center) },
                        onClick = { scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        } }
                    )

                    CommonNavigationButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                            .fillMaxHeight(),
                        label = "Friends",
                        navigateTo = {
                            handleEvent(ProfileEvent.NavigateScreen(navController, "Friends"))
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = { CommonBottomAppBar(username = profileState.username) }
        )
    }
}
