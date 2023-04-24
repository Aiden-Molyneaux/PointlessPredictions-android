package carleton.comp2601.pointlesspredictions.ui.common.prediction_pop_up

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import carleton.comp2601.pointlesspredictions.ui.common.InputType
import carleton.comp2601.pointlesspredictions.ui.common.TextInput

@Composable
fun ExpirationDateFields(
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

val dayFocusRequester = FocusRequester()
val yearFocusRequester = FocusRequester()

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
            yearFocusRequester.requestFocus()
        }),
        focusRequester = dayFocusRequester
    )
}

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
            dayFocusRequester.requestFocus()
        }),
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