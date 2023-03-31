package carleton.comp2601.pointlesspredictions.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import carleton.comp2601.pointlesspredictions.entities.Prediction
import carleton.comp2601.pointlesspredictions.entities.User

data class UserWithPredictions(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val predictions: List<Prediction>
)