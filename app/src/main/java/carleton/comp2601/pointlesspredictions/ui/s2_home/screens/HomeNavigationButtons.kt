package carleton.comp2601.pointlesspredictions.ui.s2_home.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.ui.common.Screen
import kotlinx.coroutines.launch

@Composable
fun HomeNavigationButtons(navController: NavController, user_id: String?) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.height(50.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            onClick = { scope.launch { navController.navigate(Screen.ProfileScreen.withArgs(user_id!!)) }},
            shape = CutCornerShape(0),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(
                text = "My Predictions",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            onClick = { /*TODO*/ },
            shape = CutCornerShape(0),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(
                text = "My Friends",
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }
}
