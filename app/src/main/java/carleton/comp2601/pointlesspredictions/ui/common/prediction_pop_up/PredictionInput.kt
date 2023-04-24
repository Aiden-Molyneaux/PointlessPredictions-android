package carleton.comp2601.pointlesspredictions.ui.common.prediction_pop_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import carleton.comp2601.pointlesspredictions.ui.common.InputType
import carleton.comp2601.pointlesspredictions.ui.common.TextInput

@Composable
fun PredictionInput(
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