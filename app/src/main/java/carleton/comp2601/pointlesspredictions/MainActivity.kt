package carleton.comp2601.pointlesspredictions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import carleton.comp2601.pointlesspredictions.ui.theme.PointlessPredictionsTheme
import carleton.comp2601.pointlesspredictions.ui.theme.Shapes
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PointlessPredictionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    LandingPage()
                }
            }
        }
    }
}

@Composable
fun LandingPage() {
    Column (
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(48.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TOP ICON
        Icon(
            painter = painterResource(id = R.drawable.moon_icon2),
            contentDescription = null,
            Modifier.size(100.dp),
            tint = colors.onBackground
        )

        // APP NAME TEXT
        Text(
            text = "Pointless Predictions",
            color = colors.onBackground,
            fontSize = 64.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(
                    color = colors.secondary,
                    offset = Offset(5.0f, 10.0f),
                    blurRadius = 10f
                )
            ),
            modifier = Modifier.padding(top = 40.dp)
        )

        LoginForm()
    }
}

data class AuthState(
    val authMode: AuthMode = AuthMode.SIGN_IN,
    val username: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    fun isFormValid(): Boolean {
        return password?.isNotEmpty() == true && username?.isNotEmpty() == true // && authMode == AuthMode.SIGN_IN
    }
}

enum class AuthMode {
    SIGN_UP, SIGN_IN
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    PointlessPredictionsTheme {
////        AuthTitle(authMode = AuthMode.SIGN_IN)
//    }
//}