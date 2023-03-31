package carleton.comp2601.pointlesspredictions.entities.relations

//import androidx.room.Embedded
//import androidx.room.Junction
//import androidx.room.Relation
//import carleton.comp2601.pointlesspredictions.entities.User
//
//data class UserWithFriends(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userName1",
//        entityColumn = "userName2",
//        associateBy = Junction(UserxUserCrossRef::class)
//    )
//    val friends: List<User>
//)