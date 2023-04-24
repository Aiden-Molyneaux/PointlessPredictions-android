package carleton.comp2601.pointlesspredictions.ui.s4_friends.viewmodels

import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User

data class FriendsScreenState (
    val user_id: Int? = null,
    val username: String? = null,
    val password: String? = null,
    val predictionText: String? = null,
    val day: String? = null,
    val month: String? = null,
    val year: String? = null,
    val isChecked: Boolean = true,
    val error: String? = null,
    val users: Map<User, Boolean> = mutableMapOf<User, Boolean>(),
    val predictions: List<Prediction> = emptyList(),
    val numberOfTrue: Int? = 0,
    val numberOfFalse: Int? = 0,
    val numberOfUnconfirmed: Int? = 0
)