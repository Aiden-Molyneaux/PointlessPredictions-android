package carleton.comp2601.pointlesspredictions.ui.s4_friends.screens

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
import carleton.comp2601.pointlesspredictions.ui.common.CommonBottomAppBar
import carleton.comp2601.pointlesspredictions.ui.common.prediction_pop_up.PredictionPopUpContent
import carleton.comp2601.pointlesspredictions.ui.common.CommonNavigationButton
import carleton.comp2601.pointlesspredictions.ui.common.CommonPredictionCounter
import carleton.comp2601.pointlesspredictions.ui.s4_friends.viewmodels.FriendsViewModel
import carleton.comp2601.pointlesspredictions.ui.s4_friends.viewmodels.FriendsScreenEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
/// RENDERED AFTER SUCCESSFUL LOGIN
fun FriendsScreen(navController: NavController, dao: UserDao, user_id: String?) {
    val viewModel: FriendsViewModel = viewModel()
    val handleEvent = viewModel::handleEvent

    // on MainScreen initialization we need to access the database to fetch the user corresponding to the id passed to MainScreen
    handleEvent(FriendsScreenEvent.inititalizeFriendsScreen(user_id, dao))
    val friendsScreenState = viewModel.uiState.collectAsState().value

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    // DRAWER FOR PREDICTION POPUP
    BottomDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            PredictionPopUpContent(
                username = friendsScreenState.username,
                predictionText = friendsScreenState.predictionText,
                day = friendsScreenState.day,
                month = friendsScreenState.month,
                year = friendsScreenState.year,
                isChecked = friendsScreenState.isChecked,
                drawerState = drawerState,
                onPredictionTextChanged = {
                    handleEvent(FriendsScreenEvent.PredictionTextChanged(it))
                },
                onExpirationCheckedChanged = {
                    handleEvent(FriendsScreenEvent.ExpirationCheckChanged(it))
                },
                onDayChanged = {
                    handleEvent(FriendsScreenEvent.DayChanged(it))
                },
                onMonthChanged = {
                    handleEvent(FriendsScreenEvent.MonthChanged(it))
                },
                onYearChanged = {
                    handleEvent(FriendsScreenEvent.YearChanged(it))
                },
                onPredictionSubmitted = {
                    handleEvent(FriendsScreenEvent.PredictionSubmitted(
                        predictionText = friendsScreenState.predictionText?:"",
                        isChecked = friendsScreenState.isChecked,
                        day = friendsScreenState.day?:"",
                        month = friendsScreenState.month?:"",
                        year = friendsScreenState.year?:"",
                        user_id = friendsScreenState.user_id?:0,
                        dao = dao
                    ))
                    scope.launch { drawerState.close() }
                }
            )
        }
    ){
        // MAIN PAGE CONTENT
        Scaffold(
            topBar = {
                FriendsTopAppBar(
                    onLogoutClicked = {
                        handleEvent(FriendsScreenEvent.Logout(navController))
                    }
                )
            },
            content = {
                Column(modifier = Modifier
                    .fillMaxHeight()
                ){
                    Box(modifier = Modifier.fillMaxWidth()){
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom){
                            CommonPredictionCounter(
                                numberOfTrue = friendsScreenState.numberOfTrue!!,
                                numberOfFalse = friendsScreenState.numberOfFalse!!,
                                numberOfUnconfirmed = friendsScreenState.numberOfUnconfirmed!!
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Top) {
                            FriendsDropDown(
                                usersAndFriendshipStatus = friendsScreenState.users,
                                changeFriendStatus = { user, newStatus ->
                                    handleEvent(FriendsScreenEvent.FriendStatusChanged(user, newStatus, dao))
                                }
                            )
                        }
                    }

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
                        FriendsFeedWindow(
                            friendsScreenState.predictions
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
                            handleEvent(FriendsScreenEvent.NavigateScreen(navController, "Home"))
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
                        label = "Profile",
                        navigateTo = {
                            handleEvent(FriendsScreenEvent.NavigateScreen(navController, "Profile"))
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = { CommonBottomAppBar(username = friendsScreenState.username) }
        )
    }
}