package carleton.comp2601.pointlesspredictions.entities.relations

//import androidx.room.Embedded
//import androidx.room.Junction
//import androidx.room.Relation
//import carleton.comp2601.pointlesspredictions.entities.User
//
//data class UserWithFriends(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "user_id",
//        entityColumn = "user_id",
//        associateBy = Junction(UserFriendCrossRef::class)
//    )
//    val friends: List<User>
//)