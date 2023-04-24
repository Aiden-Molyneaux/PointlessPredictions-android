package carleton.comp2601.pointlesspredictions.ui.s4_friends.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carleton.comp2601.pointlesspredictions.entities.User
import carleton.comp2601.pointlesspredictions.ui.common.rememberWindowInfo
import carleton.comp2601.pointlesspredictions.ui.theme.falseRed
import carleton.comp2601.pointlesspredictions.ui.theme.trueGreen

@Composable
fun FriendsDropDown(
    usersAndFriendshipStatus: Map<User, Boolean>,
    changeFriendStatus: (user: User, newStatus: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Option 1") }

    val windowInfo = rememberWindowInfo()
    val columnWidth = (windowInfo.screenWidth/2)

    Column(){
        Button(
            modifier = Modifier.padding(top = 6.dp, end = 20.dp).wrapContentHeight(),
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ){
            Text(
                text = "Add Friends",
                color = Color.Black,
                fontSize = 16.sp
            )
        }

        DropdownMenu(
            modifier = Modifier
                .width(columnWidth)
                .background(color = MaterialTheme.colors.background)
                .border(BorderStroke(2.dp, MaterialTheme.colors.onSecondary)),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            content = {
                usersAndFriendshipStatus.forEach {
                    DropdownMenuItem(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.onBackground,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(
                                width = 2.dp,
                                MaterialTheme.colors.onSecondary,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .fillMaxWidth(),
                        onClick = {
                            selectedOption = it.key.userName
                            expanded = false
                        },
                    ){
                        Icon(
                            modifier = Modifier.offset(x = (-10).dp),
                            imageVector = Icons.Default.Person,
                            contentDescription = ""
                        )
                        Text(it.key.userName)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ){
                            if(it.value) {
                                addRemoveButton(
                                    user = it.key,
                                    status = "removed",
                                    color = falseRed,
                                    icon = Icons.Default.Remove,
                                    contentDescription = "",
                                    changeFriendStatus = changeFriendStatus
                                )
                            } else {
                                addRemoveButton(
                                    user = it.key,
                                    status = "added",
                                    color = trueGreen,
                                    icon = Icons.Default.Add,
                                    contentDescription = "",
                                    changeFriendStatus = changeFriendStatus
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun addRemoveButton(
    user: User,
    status: String,
    color: Color,
    icon: ImageVector,
    contentDescription: String,
    changeFriendStatus: (user: User, newStatus: String) -> Unit
){
    Button(
        modifier = Modifier
            .height(20.dp)
            .width(20.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = { changeFriendStatus(user, status) },
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.Center),
                contentDescription = contentDescription,
                tint = Color.White
            )
        }
    }
}