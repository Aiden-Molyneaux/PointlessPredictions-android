import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.*
import carleton.comp2601.pointlesspredictions.entities.Prediction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
/// RENDERED AFTER SUCCESSFUL LOGIN
fun UserScreen(navController: NavController, repo: UserRepository, dao: UserDao, user_id: String?) {
    val viewModel: MainViewModel = viewModel()
    val handleEvent = viewModel::handleEvent

    // on MainScreen initialization we need to access the database to fetch the user corresponding to the id passed to MainScreen
    handleEvent(MainEvent.inititalizeMainScreen(user_id, dao))
    val mainState = viewModel.uiState.collectAsState().value

    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)

    // DRAWER FOR PREDICTION POPUP
    BottomDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            MainPredictionPopUp(
                username = mainState.username,
                predictionText = mainState.predictionText,
                day = mainState.day,
                month = mainState.month,
                year = mainState.year,
                isChecked = mainState.isChecked,
                drawerState = drawerState,
                onPredictionTextChanged = {
                    handleEvent(MainEvent.PredictionTextChanged(it))
                },
                onExpirationCheckedChanged = {
                    handleEvent(MainEvent.ExpirationCheckChanged(it))
                },
                onDayChanged = {
                    handleEvent(MainEvent.DayChanged(it))
                },
                onMonthChanged = {
                    handleEvent(MainEvent.MonthChanged(it))
                },
                onYearChanged = {
                    handleEvent(MainEvent.YearChanged(it))
                },
                onPredictionSubmitted = {
                    handleEvent(MainEvent.PredictionSubmitted(predictionText = mainState.predictionText?:"", isChecked = mainState.isChecked, day = mainState.day?:"", month = mainState.month?:"", year = mainState.year?:"", user_id = mainState.user_id?:0, dao = dao))
                    scope.launch { drawerState.close() }
                }
            )
        }
    ){
        // MAIN PAGE CONTENT
        Scaffold(
            topBar = { MainTopAppBar() },
            content = {
                Column(modifier = Modifier
                    .fillMaxHeight()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 120.dp)
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
                        MainPredictionWindow(mainState.predictionList)
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Make a Prediction", fontSize = 16.sp) },
                    onClick = { scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    } }
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = { MainBottomAppBar(username = mainState.username)}
        )
    }
}

@Composable
fun MainTopAppBar() {
    TopAppBar(
        modifier = Modifier.height(50.dp),
        backgroundColor = MaterialTheme.colors.onSecondary,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Howdy, Pointless Predictor!",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif,
                style = TextStyle(
                    shadow = Shadow(
                        color = MaterialTheme.colors.secondary,
                        offset = Offset(5.0f, 5.0f),
                        blurRadius = 10f
                    )
                )
            )
        }
    }
}

@Composable
fun MainPredictionWindow(predictionList: List<Prediction>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(predictionList) {prediction ->
            PredictionRow(prediction)
        }
    }
}

@Composable
fun PredictionRow(prediction: Prediction){
    Column(modifier = Modifier
        .width(360.dp)
        .background(color = MaterialTheme.colors.secondary)
        .border(2.dp, MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(10.dp))
        .padding(8.dp)
    ) {
        Column() {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = prediction.premise,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Divider(
                color = MaterialTheme.colors.onBackground,
                thickness = 1.dp,
                modifier = Modifier.padding(4.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Predicted on: " + prediction.datePlaced,
                    modifier = Modifier.weight(1f),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
                if(prediction.expirationDate != ""){
                    Text(
                        text = "Expires on: " + prediction.expirationDate,
                        modifier = Modifier.weight(1f),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainPredictionPopUp(
    username: String?,
    predictionText: String?,
    day: String?,
    month: String?,
    year: String?,
    isChecked: Boolean,
    drawerState: BottomDrawerState,
    onPredictionTextChanged: (predictionText: String) -> Unit,
    onExpirationCheckedChanged: (Boolean) -> Unit,
    onDayChanged: (day: String) -> Unit,
    onMonthChanged: (month: String) -> Unit,
    onYearChanged: (year: String) -> Unit,
    onPredictionSubmitted: () -> Unit
){
    val windowInfo = rememberWindowInfo()
    val columnSize = (windowInfo.screenHeight/2)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(columnSize)
            .width(200.dp)
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onBackground))

        TopAppBar(
            modifier = Modifier.height(45.dp),
            backgroundColor = MaterialTheme.colors.onSecondary,
        ) {

            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
                Button(
                    modifier = Modifier.width(50.dp),
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                ){
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.CenterStart),
                            contentDescription = "drawable icons",
                            tint = Color.Unspecified
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = (-27).dp),
                    text = "It's time to get Mystical",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                    style = TextStyle(
                        shadow = Shadow(
                            color = MaterialTheme.colors.secondary,
                            offset = Offset(5.0f, 5.0f),
                            blurRadius = 10f
                        )
                    )
                )
            }
        }

        Spacer(modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onBackground))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Checkbox(checked = isChecked, onCheckedChange = onExpirationCheckedChanged)

            Text("add an expiration date?", modifier = Modifier.padding(top = 5.dp))
        }

        MainPredictionInput(username = username, predictionText = predictionText, onPredictionTextChanged = onPredictionTextChanged)

        if(isChecked) {
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            MainExpirationDate(
                day = day,
                month = month,
                year = year,
                onDayChanged = onDayChanged,
                onMonthChanged = onMonthChanged,
                onYearChanged = onYearChanged,
            )
        }

        Button(
            onClick = onPredictionSubmitted,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary))
        {
            Text(
                text = "Predict the Future",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun MainPredictionInput(
    username: String?,
    predictionText: String?,
    onPredictionTextChanged: (predictionText: String) -> Unit,
){
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            inputType = InputType.PredictionText,
            inValue = predictionText?: "",
            onValueChange = onPredictionTextChanged,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        Row(modifier = Modifier.padding(top = 2.dp)){
            Text(
                text = "Example: $username"
            )
            Text(
                text = " predicts this is their year!"
            )
        }

    }
}

@Composable
fun MainExpirationDate(
    day: String?,
    month: String?,
    year: String?,
    onDayChanged: (day: String) -> Unit,
    onMonthChanged: (month: String) -> Unit,
    onYearChanged: (year: String) -> Unit
) {
    Text(text = "Expiration Date", modifier = Modifier.offset(y = 10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MonthInput(month = month, onMonthChanged = onMonthChanged)
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DayInput(day = day, onDayChanged = onDayChanged)
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            YearInput(year = year, onYearChanged = onYearChanged)
        }
    }
}

val monthFocusRequester = FocusRequester()

@Composable
fun DayInput(
    day: String?,
    onDayChanged: (day: String) -> Unit,
){
    TextInput(
        inputType = InputType.Day,
        inValue = day ?: "",
        onValueChange = { day ->
            onDayChanged(day)
        },
        keyboardActions = KeyboardActions(onNext = {
            monthFocusRequester.requestFocus()
        })
    )
}

val yearFocusRequester = FocusRequester()

@Composable
fun MonthInput(
    month: String?,
    onMonthChanged: (day: String) -> Unit,
){
    TextInput(
        inputType = InputType.Month,
        inValue = month ?: "",
        onValueChange = { month ->
            onMonthChanged(month)
        },
        keyboardActions = KeyboardActions(onNext = {
            yearFocusRequester.requestFocus()
        }),
        focusRequester = monthFocusRequester
    )
}

@Composable
fun YearInput(
    year: String?,
    onYearChanged: (year: String) -> Unit,
){
    val focusManager = LocalFocusManager.current

    TextInput(
        inputType = InputType.Year,
        inValue = year ?: "",
        onValueChange = { year ->
            onYearChanged(year)
        },
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        focusRequester = yearFocusRequester
    )
}

@Composable
fun MainBottomAppBar(username: String?) {
    BottomAppBar(
        modifier = Modifier.height(40.dp),
        backgroundColor = MaterialTheme.colors.onSecondary
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (10).dp),
                text = "Logged in as " + username,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}