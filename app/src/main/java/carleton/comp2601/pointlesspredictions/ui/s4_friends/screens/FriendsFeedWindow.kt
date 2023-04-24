package carleton.comp2601.pointlesspredictions.ui.s4_friends.screens

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.ui.theme.Shapes
import carleton.comp2601.pointlesspredictions.ui.theme.falseRed
import carleton.comp2601.pointlesspredictions.ui.theme.tiffanyBlue
import carleton.comp2601.pointlesspredictions.ui.theme.trueGreen

@Composable
fun FriendsFeedWindow(
    predictionList: List<Prediction>
){
    var lazyListState = rememberLazyListState()
    if(predictionList.size!=0){
        lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = predictionList.size)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        reverseLayout = true,
        state = lazyListState
    ){
        items(predictionList) {prediction ->
            ProfileFeedPredictionEntry(prediction)
        }
    }

    LaunchedEffect(predictionList.size){
        lazyListState.scrollToItem(predictionList.size)
    }
}

@Composable
fun ProfileFeedPredictionEntry(
    prediction: Prediction
){
    var color = tiffanyBlue
    if(prediction.confirmationStatus == true){
        color = trueGreen
    }
    else if(prediction.confirmationStatus == false){
        color = falseRed
    }

    Row(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(modifier = Modifier
            .height(IntrinsicSize.Min)
            .background(color = color, shape = RoundedCornerShape(10.dp))
            .border(2.dp, MaterialTheme.colors.onSecondary, shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
            .weight(2f)
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
                    color = MaterialTheme.colors.onSecondary,
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
}


