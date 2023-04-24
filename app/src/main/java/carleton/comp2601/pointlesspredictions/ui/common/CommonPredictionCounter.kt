package carleton.comp2601.pointlesspredictions.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import carleton.comp2601.pointlesspredictions.ui.theme.chineseViolet
import carleton.comp2601.pointlesspredictions.ui.theme.falseRed
import carleton.comp2601.pointlesspredictions.ui.theme.tiffanyBlue
import carleton.comp2601.pointlesspredictions.ui.theme.trueGreen

@Composable
fun CommonPredictionCounter(
    numberOfTrue: Int,
    numberOfFalse: Int,
    numberOfUnconfirmed: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier.width(166.dp).padding(top = 8.dp, start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PredictionCounter(count = numberOfTrue, trueGreen)

            PredictionCounter(count = numberOfFalse, falseRed)

            PredictionCounter(count = numberOfUnconfirmed, tiffanyBlue)
        }
    }

}

@Composable
fun PredictionCounter(count: Int, color: Color) {
    Box(
        modifier = Modifier.width(50.dp).border(border = BorderStroke(2.dp, chineseViolet), shape = CircleShape).background(color = color, shape = CircleShape).padding(8.dp).wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}