package carleton.comp2601.pointlesspredictions.ui.s1_authentication.screens

import androidx.compose.foundation.layout.fillMaxWidth
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
import carleton.comp2601.pointlesspredictions.R
import carleton.comp2601.pointlesspredictions.ui.s1_authentication.viewmodels.AuthMode

@Composable
fun ToggleAuthModeButton(
    modifier: Modifier = Modifier,
    authMode: AuthMode,
    altText: Boolean = false, // altText is only true when device is in non-compact mode
    toggleAuth: () -> Unit
){
    Button(onClick = { toggleAuth() }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary), modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(
                if (authMode == AuthMode.SIGN_IN && altText) {
                    R.string.action_need_account_alt_text
                }
                else if (authMode == AuthMode.SIGN_IN) {
                    R.string.action_need_account
                }
                else if (authMode == AuthMode.SIGN_UP && altText) {
                    R.string.action_already_have_account_alt_text
                }
                else {
                    R.string.action_already_have_account
                }
            ),
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}