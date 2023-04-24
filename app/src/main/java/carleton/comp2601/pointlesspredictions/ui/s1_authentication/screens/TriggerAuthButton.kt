package carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import carleton.comp2601.pointlesspredictions.R
import carleton.comp2601.pointlesspredictions.data.UserDao
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels.AuthMode

@Composable
fun TriggerAuthButton (
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    enableAuthentication: Boolean,
    navController: NavController,
    dao: UserDao,
    onAuthenticate: () -> Unit
){
    Button(
        onClick = {
            onAuthenticate()
        },
        enabled = enableAuthentication,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = modifier
    ) {
        Text(
            text = stringResource(
                if (authMode == AuthMode.SIGN_IN) {
                    R.string.action_sign_in
                } else {
                    R.string.action_sign_up
                }
            ),
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}