package carleton.comp2601.pointlesspredictions

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import carleton.comp2601.pointlesspredictions.data.UserDatabase
import carleton.comp2601.pointlesspredictions.entities.Friendship
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User
import carleton.comp2601.pointlesspredictions.ui.theme.PointlessPredictionsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = UserDatabase.getInstance(this).userDao

        val users = listOf(
            User(1, "Aiden", "123"),
            User(2, "Kyra", "123"),
            User(3, "Pat", "123"),
            User(4, "Zack", "123"),
            User(5, "TimTheeCat", "123"),
            User(6, "Anil", "123")
        )

        val predictions = listOf(
            Prediction(1, 1, "Aiden predicts that Zack will find love in 2023", "2023-03-01", "2023-12-31"),
            Prediction(2, 2, "Kyra predicts that her sickness will pass quickly", "2023-03-02", "", false),
            Prediction(3, 6, "Anil predicts that he will move to Ottawa by 2024", "2023-03-04", "2024-12-31"),
            Prediction(4, 3, "Pat predicts that this 3804 assignment will go better than the last", "2023-03-07", "", true),
            Prediction(5, 2, "Kyra predicts that biotechnology will create a lab-grown meat that will be more popular to tradition meat", "2023-03-10", "2025-12-31"),
            Prediction(6, 4, "Zack predicts that he will enjoy his new job", "2023-03-11", "", false),
            Prediction(7, 2, "Kyra predicts that Pat will do well on his exams this term", "2023-03-20", "2023-05-01"),
            Prediction(8, 3, "Pat predicts that Kyra has misjudged his ability, and will be wrong", "2023-03-25", "2023-05-01"),
            Prediction(9, 1, "Aiden predicts that electric cars will be more affordable than gas-power cars", "2023-03-27", "2030-12-31"),
            Prediction(10, 4, "Zack predicts that Pat will come up with a side hustle that generates additional income", "2023-04-01", "2023-12-31"),
            Prediction(11, 5, "TimTheeCat the Cat predicts that his new toy will be sooo much fun", "2023-04-08", "", true),
            Prediction(12, 1, "Aiden predicts that TimTheeCat will only play with his new toy for an hour", "2023-04-08", "2023-04-09", true),
            Prediction(13, 6, "Anil predicts that his cat will never stop chewing wires", "2023-04-12", "", true),
            Prediction(14, 1, "Aiden predicts that this presentation goes well", "2023-04-12", "2023-04-13")
        )

        val friendships = listOf(
            Friendship(1, 2),
            Friendship(1, 3),
            Friendship(1, 4),
            Friendship(1, 5),
            Friendship(2, 3),
            Friendship(2, 5),
            Friendship(3, 4),
        )

        lifecycleScope.launch {
            users.forEach { dao.addUser(it) }
            predictions.forEach { dao.upsertPrediction(it) }
            friendships.forEach { dao.addFriendship(it) }

            val friends = dao.getFriendsOfUser(1)
            friends.forEach {
                Log.d(it.user_id1.toString(), it.user_id2.toString())
            }
        }

        setContent {
            PointlessPredictionsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    Navigation(context = this)
                }
            }
        }
    }
}