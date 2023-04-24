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
import carleton.comp2601.pointlesspredictions.ui.s2_home.screens.HomeFeedWindow
import carleton.comp2601.pointlesspredictions.ui.s2_home.screens.HomeNavigationButtons
import carleton.comp2601.pointlesspredictions.ui.s2_home.screens.HomeTopAppBar
import carleton.comp2601.pointlesspredictions.ui.s2_home.viewmodels.HomeEvent
import carleton.comp2601.pointlesspredictions.ui.s3_profile.viewmodels.ProfileEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
/// RENDERED AFTER SUCCESSFUL LOGIN
fun HomeScreen(navController: NavController, repo: UserRepository, dao: UserDao, user_id: String?) {
    val viewModel: HomeViewModel = viewModel()
    val handleEvent = viewModel::handleEvent

    // on MainScreen initialization we need to access the database to fetch the user corresponding to the id passed to MainScreen
    handleEvent(HomeEvent.inititalizeHomeScreen(user_id, dao))
    val homeState = viewModel.uiState.collectAsState().value

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    // DRAWER FOR PREDICTION POPUP
    BottomDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            PredictionPopUpContent(
                username = homeState.username,
                predictionText = homeState.predictionText,
                day = homeState.day,
                month = homeState.month,
                year = homeState.year,
                isChecked = homeState.isChecked,
                drawerState = drawerState,
                onPredictionTextChanged = {
                     handleEvent(HomeEvent.PredictionTextChanged(it))
                },
                onExpirationCheckedChanged = {
                    handleEvent(HomeEvent.ExpirationCheckChanged(it))
                },
                onDayChanged = {
                    handleEvent(HomeEvent.DayChanged(it))
                },
                onMonthChanged = {
                    handleEvent(HomeEvent.MonthChanged(it))
                },
                onYearChanged = {
                    handleEvent(HomeEvent.YearChanged(it))
                },
                onPredictionSubmitted = {
                    handleEvent(HomeEvent.PredictionSubmitted(predictionText = homeState.predictionText?:"", isChecked = homeState.isChecked, day = homeState.day?:"", month = homeState.month?:"", year = homeState.year?:"", user_id = homeState.user_id?:0, dao = dao))
                    scope.launch { drawerState.close() }
                }
            )
        }
    ){
        // MAIN PAGE CONTENT
        Scaffold(
            topBar = { HomeTopAppBar() },
            content = {
                Column(modifier = Modifier
                    .fillMaxHeight()
                ){
                    CommonPredictionCounter(
                        numberOfTrue = homeState.numberOfTrue!!,
                        numberOfFalse = homeState.numberOfFalse!!,
                        numberOfUnconfirmed = homeState.numberOfUnconfirmed!!
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
                        HomeFeedWindow(homeState.predictionList)
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
                ){
                    CommonNavigationButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                            .fillMaxHeight(),
                        label = "Profile",
                        navigateTo = {
                            handleEvent(HomeEvent.NavigateScreen(navController, "Profile"))
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
                            .padding(start = 4.dp)
                            .fillMaxHeight(),
                        label = "Friends",
                        navigateTo = {
                            handleEvent(HomeEvent.NavigateScreen(navController, "Friends"))
                        }
                    )
                }

            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = { CommonBottomAppBar(username = homeState.username)}
        )
    }
}







