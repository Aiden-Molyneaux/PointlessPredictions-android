package carleton.comp2601.pointlesspredictions.ui.common.prediction_pop_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carleton.comp2601.pointlesspredictions.ui.common.rememberWindowInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PredictionPopUpContent(
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
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
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                            //.offset(x = (-27).dp),
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
        }

//        Spacer(modifier = Modifier
//            .height(2.dp)
//            .fillMaxWidth()
//            .background(color = MaterialTheme.colors.onBackground))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Checkbox(checked = isChecked, onCheckedChange = onExpirationCheckedChanged)

            Text("add an expiration date?", modifier = Modifier.padding(top = 5.dp))
        }

        PredictionInput(username = username, predictionText = predictionText, onPredictionTextChanged = onPredictionTextChanged)

        if(isChecked) {
            Divider(
                color = Color.White.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExpirationDateFields(
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